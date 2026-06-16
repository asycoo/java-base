package day2.employee;

/**
 * 固定月薪员工
 */
public class SalariedEmployee extends Employee {

    private final double monthlySalary;

    public SalariedEmployee(String id, String name, double monthlySalary) {
        super(id, name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculatePay() {
        if (monthlySalary < 0) throw new IllegalArgumentException("月薪不能小于 0");
        return monthlySalary;
        // throw new UnsupportedOperationException("TODO: 实现 calculatePay");
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }
}
