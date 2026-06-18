package phase2.arraylist;

/**
 * SimpleArrayList 自测
 */
public class SimpleArrayListTest {

    public static void main(String[] args) {
        SimpleArrayList<String> list = new SimpleArrayList<>();

        check(0, list.size());

        list.add("a");
        list.add("b");
        list.add("c");
        check(3, list.size());
        check("a", list.get(0));
        check("b", list.get(1));
        check("c", list.get(2));

        check("b", list.remove(1));
        check(2, list.size());
        check("a", list.get(0));
        check("c", list.get(1));

        // 触发扩容：添加超过 10 个元素
        for (int i = 0; i < 12; i++) {
            list.add("x" + i);
        }
        check(14, list.size());

        try {
            list.get(100);
            throw new AssertionError("越界应抛异常");
        } catch (IndexOutOfBoundsException ignored) {
        }

        System.out.println("SimpleArrayListTest: 全部通过 ✓");
    }

    private static void check(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }
}
