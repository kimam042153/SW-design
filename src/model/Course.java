package model;

public class Course {
    private int id;
    private String name;
    private int credit;
    private int courseCode;
    private int section;
    private String dayOfWeek;
    private String professor;
    private String classroom;
    private int startHour;
    private int endHour;

    public Course(int id, String name, int credit, int courseCode, int section,
                  String dayOfWeek, String professor, String classroom,
                  int startHour, int endHour) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.courseCode = courseCode;
        this.section = section;
        this.dayOfWeek = dayOfWeek;
        this.professor = professor;
        this.classroom = classroom;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int    getId()        { return id; }
    public String getName()      { return name; }
    public int    getCredit()    { return credit; }
    public int    getCourseCode(){ return courseCode; }
    public int    getSection()   { return section; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getProfessor() { return professor; }
    public String getClassroom() { return classroom; }
    public int    getStartHour() { return startHour; }
    public int    getEndHour()   { return endHour; }

    public void setName(String name)           { this.name = name; }
    public void setCredit(int credit)          { this.credit = credit; }
    public void setCourseCode(int courseCode)  { this.courseCode = courseCode; }
    public void setSection(int section)        { this.section = section; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public void setProfessor(String professor) { this.professor = professor; }
    public void setClassroom(String classroom) { this.classroom = classroom; }
    public void setStartHour(int startHour)    { this.startHour = startHour; }
    public void setEndHour(int endHour)        { this.endHour = endHour; }

    @Override
    public String toString() {
        return String.format("%-12s %d분반  %s요일 %02d:00-%02d:00  %-8s  %s  %d학점",
                name, section, dayOfWeek, startHour, endHour, professor, classroom, credit);
    }
}
