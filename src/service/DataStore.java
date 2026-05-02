package service;

import model.Course;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static final List<User> users = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static int nextCourseId = 16;

    static {
        // 기본 사용자
        users.add(new User("admin",    "admin1234",   "관리자", "ADMIN"));
        users.add(new User("student1", "student1234", "김남형", "STUDENT"));

        // 샘플 강의 데이터
        courses.add(new Course(1,  "자료구조",       3, 1001, 1, "월", "이준호", "공학관101", 9,  11));
        courses.add(new Course(2,  "자료구조",       3, 1001, 2, "수", "이준호", "공학관101", 13, 15));
        courses.add(new Course(3,  "자료구조",       3, 1001, 3, "금", "이준호", "공학관102", 9,  11));
        courses.add(new Course(4,  "알고리즘",       3, 1002, 1, "화", "김민수", "공학관201", 9,  11));
        courses.add(new Course(5,  "알고리즘",       3, 1002, 2, "목", "김민수", "공학관201", 13, 15));
        courses.add(new Course(6,  "운영체제",       3, 1003, 1, "월", "박서연", "공학관301", 13, 15));
        courses.add(new Course(7,  "운영체제",       3, 1003, 2, "화", "박서연", "공학관302", 13, 15));
        courses.add(new Course(8,  "데이터베이스",   3, 1004, 1, "수", "정하늘", "공학관401", 9,  11));
        courses.add(new Course(9,  "데이터베이스",   3, 1004, 2, "금", "정하늘", "공학관401", 13, 15));
        courses.add(new Course(10, "컴퓨터네트워크", 3, 1005, 1, "목", "최유리", "공학관501", 9,  11));
        courses.add(new Course(11, "프로그래밍언어", 3, 1006, 1, "화", "이준호", "공학관101", 15, 17));
        courses.add(new Course(12, "선형대수",       2, 2001, 1, "금", "김민수", "수학관101",  11, 13));
        courses.add(new Course(13, "소프트웨어공학", 3, 1007, 1, "월", "박서연", "공학관201", 15, 17));
        courses.add(new Course(14, "이산수학",       3, 2002, 1, "수", "정하늘", "수학관201",  15, 17));
        courses.add(new Course(15, "컴퓨터구조",     3, 1008, 1, "목", "최유리", "공학관301", 15, 17));
    }

    public static List<User>   getUsers()   { return users; }
    public static List<Course> getCourses() { return courses; }

    public static int nextId() { return nextCourseId++; }
}
