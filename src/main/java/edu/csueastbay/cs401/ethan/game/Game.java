package edu.csueastbay.cs401.ethan.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.util.Pair;

import java.time.Instant;
import java.util.*;

/**
 * Abstract base for games, handles {@link Entity Entities}, updating, pausing, scheduling tasks. Override
 * {@link Game#update(double)} to add extra functionality.
 * @see Game#add(Entity)
 * @see Game#getCollisions(Collidable)
 * @see Game#schedule(double, Runnable)
 * @see Game#setPlaying(boolean)
 */
public abstract class Game {

    /**
     * The {@link Pane} the {@link Entity Entities} in this game are displayed in.
     */
    public final Pane pane;

    // The last time a game update occurred
    private Instant lastUpdate;
    // The game clock
    private final Timeline timer;
    // Total play time, for executing scheduled tasks
    private double gameTime;
    // Tasks to run, added with schedule(double, runnable)
    private final Queue<Pair<Double, Runnable>> scheduledEvents;

    // Whether the game is playing, or over. Observable for bindings
    private final BooleanProperty playing, gameOver;
    // Actual set of Entities
    private final ObservableSet<Entity> entities;
    // Read-only set of Entities
    private final ObservableSet<Entity> _entities;
    // Sets to track additions and removals
    private final Set<Entity> toAdd, toRemove;
    // Set of Entities which implement Collidable
    private final Set<Collidable> collidables;
    // Cache to minimize repeated collision checking
    private final Map<Collidable, Map<Collidable, Shape>> cache;

    /**
     * The {@link InputHandler} for this Game. Should be set by the controller, and respond to the controls used in the
     * {@link Game} implementation.
     */
    public InputHandler input;
    /**
     * The {@link Bounds} of this Game's visible area.
     * @see Game#width
     * @see Game#height
     */
    public final Bounds bounds;
    /**
     * The size of this Game
     * @see Game#bounds
     */
    public final double width, height;

    /**
     * Creates a Game with the given width and height
     * @param width the width of the game
     * @param height the height of the game
     */
    public Game(double width, double height) {
        this.width = width;
        this.height = height;
        pane = new Pane();
        pane.setPrefSize(width, height);
        pane.setMinSize(width, height);
        pane.setMaxSize(width, height);
        pane.setClip(new Rectangle(width, height));
        input = control -> false;
        bounds = new BoundingBox(0, 0, width, height);
        playing = new SimpleBooleanProperty(false);
        gameOver = new SimpleBooleanProperty(false);
        entities = FXCollections.observableSet(new HashSet<>());
        _entities = FXCollections.unmodifiableObservableSet(entities);
        toAdd = new HashSet<>();
        toRemove = new HashSet<>();
        collidables = new HashSet<>();
        cache = new HashMap<>();

        scheduledEvents = new PriorityQueue<>(Comparator.comparing(Pair::getKey));
        gameTime = 0;
        timer = new Timeline(new KeyFrame(Duration.millis(10), (e)->{
            Instant now = Instant.now();
            double delta = java.time.Duration.between(lastUpdate, now).toNanos() / 1e9;
            gameTime += delta;
            while(!scheduledEvents.isEmpty() && scheduledEvents.peek().getKey() < gameTime) {
                scheduledEvents.poll().getValue().run();
            }
            cache.clear();
            update(delta);
            commit();
            lastUpdate = now;
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Returns a read-only {@link ObservableSet} of {@link Entity Entities} within this Game
     * @return a read-only set of the entities in this game
     */
    public final ObservableSet<Entity> getEntities() {
        return _entities;
    }

    /**
     * Adds an {@link Entity} to the game.
     * @param entity the Entity to add
     */
    public final void add(Entity entity) {
        toAdd.add(entity);
    }

    /**
     * Adds multiple {@link Entity Entities} to the game.
     * @param entities the Entities to add
     */
    public final void addAll(Collection<Entity> entities) {
        toAdd.addAll(entities);
    }

    /**
     * Removes an {@link Entity} from the game.
     * @param entity the Entity to remove
     */
    public final void remove(Entity entity) {
        toRemove.add(entity);
    }

    /**
     * Removes multiple {@link Entity Entities} from the game.
     * @param entities the Entities to remove
     */
    public final void removeAll(Collection<Entity> entities) {
        toRemove.addAll(entities);
    }

    /**
     * Schedules a task to execute after the given delay, in seconds. When the specified game time (meaning only when
     * {@link Game#isPlaying()} is {@code true}) has passed , the task will be run.
     * @param delay how long, in seconds, to wait
     * @param task the task to run
     */
    public final void schedule(double delay, Runnable task) {
        scheduledEvents.add(new Pair<>(gameTime+delay, task));
    }

    /**
     * Sets the game to playing ({@code true}) or paused ({@code false}).
     * @param playing whether the game should be playing
     */
    public final void setPlaying(boolean playing) {
        if(this.playing.get() != playing) {
            if(playing) {
                lastUpdate = Instant.now();
                timer.play();
            } else {
                timer.pause();
            }
            this.playing.set(playing);
        }
    }

    /**
     * Returns whether the game to playing ({@code true}) or paused ({@code false}).
     * @return whether the game is playing
     */
    public final boolean isPlaying() {
        return playing.get();
    }

    /**
     * Controls whether the game is playing. If false, the game is paused and {@link Game#update(double)} is not called.
     * @return
     */
    public final BooleanProperty playingProperty() { return playing; }

    public final void setGameOver(boolean gameOver) {
        this.gameOver.set(gameOver);
    }

    public final boolean isGameOver() {
        return gameOver.get();
    }

    public final BooleanProperty gameOverProperty() { return gameOver; }

    protected void update(double delta) {
        entities.forEach(e->e.update(delta));
    }

    public <T extends Collidable> Set<Collision<T>> getCollisionsWithType(Collidable collidable, Class<T> type) {
        Set<Collision<T>> out = new HashSet<>();
        Map<Collidable, Shape> localCache = cache.getOrDefault(collidable, Collections.emptyMap());
        Bounds boundsInScene = collidable.getCollisionShape().localToScene(collidable.getCollisionShape().getBoundsInLocal());
        for(Collidable c : collidables) {
            if(c == collidable) continue; // Don't collide with yourself
            if(type.isAssignableFrom(c.getClass())) {
                T other = type.cast(c);
                Shape intersect;
                if(localCache.containsKey(other)) {
                    intersect = localCache.get(other);
                } else {
                    // Check bounds first to avoid expensive Shape.intersect if possible
                    if(other.getCollisionShape().localToScene(other.getCollisionShape().getBoundsInLocal()).intersects(boundsInScene)) {
                        intersect = Shape.intersect(collidable.getCollisionShape(), other.getCollisionShape());
                    } else {
                        intersect = null;
                    }
                    cache.putIfAbsent(other, new HashMap<>());
                    cache.get(other).put(collidable, intersect);
                }
                if(intersect != null && intersect.getBoundsInLocal().getWidth() > 0) {
                    out.add(new Collision<T>(other, intersect));
                }
            }

        }
        return out;
    }

    public Set<Collision<Collidable>> getCollisions(Collidable collidable) {
        return getCollisionsWithType(collidable, Collidable.class);
    }

    protected void commit() {
        for(Entity e : toAdd) {
            if(entities.add(e)) {
                e.game = this;
                pane.getChildren().add(e.root);
                if(e instanceof Collidable c) {
                    collidables.add(c);
                }
            }
        }
        toAdd.clear();
        for(Entity e : toRemove) {
            if(entities.remove(e)) {
                pane.getChildren().remove(e.root);
                if(e instanceof Collidable c) {
                    collidables.remove(c);
                }
            }
        }
        toRemove.clear();
        entities.forEach(Entity::commit);
    }
}
