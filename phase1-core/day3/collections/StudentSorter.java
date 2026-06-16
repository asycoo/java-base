package day3.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 实操 3.1 - Student 排序
 *
 * 要求：
 * - 成绩降序（高分在前）
 * - 成绩相同则姓名升序
 * - 使用 Comparator，不要手动冒泡排序
 */
public class StudentSorter {

    public static List<Student> sort(List<Student> students) {
        // TODO: 复制列表后排序，不修改原列表
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort(Comparator.comparing(Student::getScore).reversed().thenComparing(Student::getName));
        return sortedStudents;
        // 提示：Comparator.comparing(Student::getScore).reversed()
        //              .thenComparing(Student::getName)
        // throw new UnsupportedOperationException("TODO: 实现 sort");
    }

    public static void main(String[] args) {
        List<Student> input = List.of(
                new Student("Bob", 85),
                new Student("Alice", 92),
                new Student("Carol", 85),
                new Student("Dave", 92)
        );

        List<Student> sorted = sort(input);

        check("Alice(92)", sorted.get(0).toString());
        check("Dave(92)", sorted.get(1).toString());
        check("Bob(85)", sorted.get(2).toString());
        check("Carol(85)", sorted.get(3).toString());

        System.out.println("排序结果：" + sorted);
        System.out.println("StudentSorter: 全部通过 ✓");
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }
}
