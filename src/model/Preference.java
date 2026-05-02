package model;

import java.util.List;

public class Preference {
    private List<String> courseNames;
    private int targetCredits;
    private List<String> freeDays;
    private List<Integer> freeHours;
    private List<String> preferredProfessors;
    private double freeDayWeight;
    private double professorWeight;
    private double creditWeight;

    public Preference(List<String> courseNames, int targetCredits,
                      List<String> freeDays, List<Integer> freeHours,
                      List<String> preferredProfessors,
                      double freeDayWeight, double professorWeight, double creditWeight) {
        this.courseNames = courseNames;
        this.targetCredits = targetCredits;
        this.freeDays = freeDays;
        this.freeHours = freeHours;
        this.preferredProfessors = preferredProfessors;
        this.freeDayWeight = freeDayWeight;
        this.professorWeight = professorWeight;
        this.creditWeight = creditWeight;
    }

    public List<String>  getCourseNames()         { return courseNames; }
    public int           getTargetCredits()        { return targetCredits; }
    public List<String>  getFreeDays()             { return freeDays; }
    public List<Integer> getFreeHours()            { return freeHours; }
    public List<String>  getPreferredProfessors()  { return preferredProfessors; }
    public double        getFreeDayWeight()        { return freeDayWeight; }
    public double        getProfessorWeight()      { return professorWeight; }
    public double        getCreditWeight()         { return creditWeight; }
}
