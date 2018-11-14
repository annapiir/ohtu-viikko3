/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.util.ArrayList;

/**
 *
 * @author annapiir
 */
public class Course {
    private String fullName;
    private ArrayList<Submission> submissions;
    private String name;
    private ArrayList<Integer> exercises;
    private int week;

    public Course(String fullName, String name, ArrayList<Integer> exercises, int week) {
        this.fullName = fullName;
        this.name = name;
        this.exercises = exercises;
        this.week = week;
        this.submissions = new ArrayList<Submission>();
    }
    
    
    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
    
    public ArrayList<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Integer> exercises) {
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubmissions(ArrayList<Submission> submissions) {
        this.submissions = submissions;
    }

    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }
    

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        String viikko = "";
        
        for (int i = 1; i <= this.week; i++) {
            
            Submission sub = null;
            
            for (Submission submission : this.submissions) {
                if (submission.getWeek() == i) {
                    sub = submission;
                }
            }
            
            if (sub == null) {
                continue;
            }
            
            int exerciseCount = 0;
            
            if (!sub.getExercises().isEmpty()) {
                exerciseCount = sub.getExercises().size();
            }
            
            viikko += "Viikko " + i + ":\n  tehtyjä tehtäviä " + 
                    exerciseCount + "/" + 
                    this.exercises.get(i) + 
                    sub.toString() + "\n";
        }
        
        return "" + this.fullName + "\n" + viikko;
    }

    
    
    
}
