package edu.csueastbay.cs401.ethan.game;

import javafx.scene.shape.Shape;

public record Collision(Collidable other, Shape intersection) {
}
