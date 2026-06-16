package day1.calculator;

/**
 * 实操 1.2 - 简易计算器
 *
 * 要求：
 * - 支持 + - * / 四则运算
 * - 除零时抛出 ArithmeticException
 * - 拆成三个方法：parse / calculate / formatResult
 *
 * 示例：
 *   calculate(10, '+', 3) → 13
 *   formatResult(10, '+', 3, 13) → "10 + 3 = 13"
 */
public class Calculator {

    /**
     * 解析用户输入，如 "10 + 3" → [10, '+', 3]
     * 非法格式抛 IllegalArgumentException
     */
    public static double[] parse(String expression) {
        if (expression == null || expression.isEmpty()) throw new IllegalArgumentException("表达式不能为空");
        String[] tokens = expression.split(" ");
        if (tokens.length != 3) throw new IllegalArgumentException("表达式格式错误");
        double[] result = new double[3];
        result[0] = Double.parseDouble(tokens[0]);
        result[1] = tokens[1].charAt(0);
        result[2] = Double.parseDouble(tokens[2]);
        return result;
        // TODO: 解析表达式，返回 [operand1, operatorAsCode, operand2]
        // 提示：operator 可用 char 强转存到 double 里不方便，可改用单独返回 char，
        //       或定义 ParseResult 记录类（推荐后者，更清晰）
        // throw new UnsupportedOperationException("TODO: 实现 parse 方法");
    }

    public static double calculate(double a, char operator, double b) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("除数不能为 0");
                return a / b;
            default:
                throw new IllegalArgumentException("非法运算符");
        }
        // TODO: 根据运算符计算，除零抛 ArithmeticException
        // throw new UnsupportedOperationException("TODO: 实现 calculate 方法");
    }

    public static String formatResult(double a, char operator, double b, double result) {
        return a + " " + operator + " " + b + " = " + result;
        // TODO: 返回如 "10.0 + 3.0 = 13.0"
        // throw new UnsupportedOperationException("TODO: 实现 formatResult 方法");
    }

    public static void main(String[] args) {
        check(13, calculate(10, '+', 3));
        check(7, calculate(10, '-', 3));
        check(30, calculate(10, '*', 3));
        check(3.333, calculate(10, '/', 3), 0.001);

        try {
            calculate(1, '/', 0);
            throw new AssertionError("除零应抛 ArithmeticException");
        } catch (ArithmeticException ignored) {
        }

        System.out.println(formatResult(10, '+', 3, 13));
        System.out.println("Calculator: 全部通过 ✓");
    }

    private static void check(double expected, double actual) {
        check(expected, actual, 0.0001);
    }

    private static void check(double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }
}
