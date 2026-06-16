import java.util.List;

/**
 * 利用多态计算一组员工的总工资
 *
 * 前端对照：像一组组件都实现了 render()，父组件统一调用，不用关心具体类型
 */
public class PayrollService {

    public double calculateTotalPay(List<Employee> employees) {
        // TODO: 遍历 employees，累加 calculatePay()
        double total = 0;
        for (Employee employee : employees) {
            total += employee.calculatePay();
        }
        return total;
        // throw new UnsupportedOperationException("TODO: 实现 calculateTotalPay");
    }

    public static void main(String[] args) {
        List<Employee> staff = List.of(
                new SalariedEmployee("E001", "Alice", 15000),
                new HourlyEmployee("E002", "Bob", 100, 160),
                new SalariedEmployee("E003", "Carol", 12000)
        );

        PayrollService service = new PayrollService();
        double total = service.calculateTotalPay(staff);
        // 15000 + 16000 + 12000 = 43000
        if (Math.abs(total - 43000) > 0.01) {
            throw new AssertionError("期望 43000，实际 " + total);
        }
        System.out.println("总工资：" + total);
        System.out.println("PayrollService: 全部通过 ✓");
    }
}
