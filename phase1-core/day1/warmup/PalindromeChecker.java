package day1.warmup;

/**
 * 实操 1.1 - 判断回文字符串
 *
 * 要求：
 * - 忽略大小写： "Level" → true
 * - 只考虑字母数字，忽略空格和标点（进阶，可选）
 * - null 返回 false
 *
 * 示例：isPalindrome("level") → true，isPalindrome("hello") → false
 */
public class PalindromeChecker {

    public static boolean isPalindrome(String input) {
        if (input == null) return false;
        if (input.isEmpty()) return true;
        input = input.toLowerCase();
        int left = 0;
        int right = input.length() - 1;
        while (left < right) {
            if (input.charAt(left) != input.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
        // TODO: 实现回文判断
        // throw new UnsupportedOperationException("TODO: 实现 isPalindrome 方法");
    }

    public static void main(String[] args) {
        check(true, isPalindrome("level"));
        check(true, isPalindrome("Level"));
        check(false, isPalindrome("hello"));
        check(true, isPalindrome("a"));
        check(false, isPalindrome(null));
        System.out.println("PalindromeChecker: 全部通过 ✓");
    }

    private static void check(boolean expected, boolean actual) {
        if (expected != actual) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }
}
