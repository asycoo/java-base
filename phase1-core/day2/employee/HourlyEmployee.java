/**
 * 时薪员工
 */
public class HourlyEmployee extends Employee {

    private final double hourlyRate;
    private final double hoursWorked;

    public HourlyEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculatePay() {
        // TODO: 时薪 × 工时
        if (hourlyRate < 0) throw new IllegalArgumentException("时薪不能小于 0");
        if (hoursWorked < 0) throw new IllegalArgumentException("工时不能小于 0");
        return hourlyRate * hoursWorked;
        // throw new UnsupportedOperationException("TODO: 实现 calculatePay");
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }
}
