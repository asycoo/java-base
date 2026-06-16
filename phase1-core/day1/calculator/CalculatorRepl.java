package day1.calculator;

import java.util.Scanner;

/**
 * 实操 1.3 - 日志版 REPL 计算器
 *
 * 要求：
 * - 循环读取用户输入，输入 exit 退出
 * - 每次运算打印日志：[LOG] 10 + 3 = 13
 * - 复用 Calculator 的 parse / calculate / formatResult
 * - 非法输入友好提示，不崩溃
 *
 * 示例交互：
 *   > 10 + 3
 *   [LOG] 10.0 + 3.0 = 13.0
 *   > exit
 *   再见！
 */
public class CalculatorRepl {

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("简易计算器（输入 exit 退出）");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("再见！");
                break;
            }

            if (line.isEmpty()) {
                continue;
            }

            try {
                // TODO: 调用 Calculator.parse → calculate → 打印 [LOG] + formatResult
                double[] result = Calculator.parse(line);
                double resultValue = Calculator.calculate(result[0], (char) result[1], result[2]);
                System.out.println("[LOG] " + Calculator.formatResult(result[0], (char) result[1], result[2], resultValue));
            } catch (IllegalArgumentException e) {
                System.out.println("格式错误，请输入如：10 + 3");
            } catch (ArithmeticException e) {
                System.out.println("错误：除数不能为 0");
            }
        }
    }

    public static void main(String[] args) {
        run();
    }
}

