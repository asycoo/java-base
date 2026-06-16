/**
 * 实操 2.2 - 抽象员工基类
 *
 * 子类：SalariedEmployee（固定月薪）、HourlyEmployee（时薪 × 工时）
 */
public abstract class Employee {

    private final String id;
    private final String name;

    protected Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /** 计算该员工本期工资 */
    public abstract double calculatePay();

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }
}
