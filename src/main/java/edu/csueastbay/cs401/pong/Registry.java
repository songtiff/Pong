package edu.csueastbay.cs401.pong;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Registry {

    private ArrayList<StudentGame> studentGames;
    private int cursor;

    public Registry() {
        this.studentGames = new ArrayList<>();
        cursor = -1;
    }

    public void register(String student_name, String package_name, String description) {
        studentGames.add(new StudentGame(student_name, package_name, description));
    }

    public void reset() {
        Collections.sort(studentGames);
        cursor = -1;
    }

    public boolean next() {
        if (cursor < studentGames.size() - 1) {
            cursor += 1;
            return true;
        } else {
            return false;
        }
    }

    public String getStudentName() {
        return studentGames.get(cursor).student_name;
    }
    public String getPackageName() {
        return studentGames.get(cursor).package_name;
    }
    public String getDescription() {
        return studentGames.get(cursor).description;
    }



    private class StudentGame implements Comparable {
        private String student_name;
        private String package_name;
        private String description;

        public StudentGame(String student_name, String package_name, String description) {
            this.student_name = student_name;
            this.package_name = package_name;
            this.description = description;
        }

        @Override
        public int compareTo(Object lhs) {
            return this.student_name.compareTo(((StudentGame)lhs).student_name);
        }
    }

}
