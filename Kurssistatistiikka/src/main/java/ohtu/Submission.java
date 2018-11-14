package ohtu;

import java.util.ArrayList;

public class Submission {
    private int week;
    private int hours;
    private ArrayList<Integer> exercises;
    private String course;

    public Submission(int week, int hours, String course) {
        this.week = week;
        this.hours = hours;
        this.course = course;
        this.exercises = new ArrayList<>();
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public String getCourse() {
        return this.course;
    }


    public void setExercises(ArrayList exercises) {
        this.exercises = exercises;
    }

    public ArrayList getExercises() {
        return exercises;
    }


    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }
    
    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    @Override
    public String toString() {
        String exercise = "";
        for (int exe : exercises) {
            exercise += Integer.toString(exe) + ", ";
        }
        return " aikaa kului " + this.hours + " tuntia, tehtävät: " + exercise;
    }
    
}