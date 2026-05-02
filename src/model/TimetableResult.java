package model;

import java.util.List;

public class TimetableResult {
    private List<Course> courses;
    private double priorityScore;
    private int totalCredits;

    public TimetableResult(List<Course> courses, double priorityScore, int totalCredits) {
        this.courses = courses;
        this.priorityScore = priorityScore;
        this.totalCredits = totalCredits;
    }

    public List<Course> getCourses()     { return courses; }
    public double getPriorityScore()     { return priorityScore; }
    public int getTotalCredits()         { return totalCredits; }
}
