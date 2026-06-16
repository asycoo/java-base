/**
 * 实操 1.1 - 反转字符串
 *
 * 要求：
 * - 不能使用 StringBuilder.reverse()
 * - 考虑空字符串和 null（null 返回 null）
 *
 * 示例：reverse("hello") → "olleh"
 */
public class ReverseString {

    public static String reverse(String input) {
        // TODO: 实现反转逻辑
        if (input == null) return null;
        if (input.isEmpty()) return "";
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
        // throw new UnsupportedOperationException("TODO: 实现 reverse 方法");
    }

    public static void main(String[] args) {
        assertEq("olleh", reverse("hello"));
        assertEq("", reverse(""));
        assertEq(null, reverse(null));
        assertEq("a", reverse("a"));
        System.out.println("ReverseString: 全部通过 ✓");
    }

    private static void assertEq(String expected, String actual) {
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new AssertionError("期望 " + expected + "，实际 " + actual);
    }
}
