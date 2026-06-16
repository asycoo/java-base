package day3.collections;

/**
 * 学生模型 — 供 StudentSorter 使用
 */
public class Student implements Comparable<Student> {

    private final String name;
    private final int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    /** 默认比较：仅按姓名（练习中会改用 Comparator） */
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + "(" + score + ")";
    }
}
