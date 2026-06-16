import java.math.BigDecimal;
import java.util.Objects;

/**
 * 实操 2.1 - 不可变 Money 类
 *
 * 要求：
 * - 字段：amount（BigDecimal）、currency（String），全部 final，无 setter
 * - add / subtract：返回新 Money，不修改自身
 * - 不同 currency 运算 → IllegalArgumentException
 * - 正确重写 equals / hashCode / toString
 * - 金额比较用 BigDecimal.compareTo，不要用 double
 *
 * 前端对照：类似 TS 里 readonly 字段 + 每次运算 return 新实例，而不是 mutate
 */
public class Money {

    private final BigDecimal amount;
    private final String currency;

    public Money(String amount, String currency) {
        // TODO: 校验 currency 非空；amount 用 new BigDecimal(amount)
        if (currency == null || currency.isEmpty()) throw new IllegalArgumentException("货币不能为空");
        this.amount = new BigDecimal(amount);
        this.currency = currency; // 货币不能为空
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Money add(Money other) {
        // TODO: 校验 currency 相同，返回新 Money
        if (!this.currency.equals(other.currency)) throw new IllegalArgumentException("货币不相同");
        return new Money(this.amount.add(other.amount).toPlainString(), this.currency);
    }

    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) throw new IllegalArgumentException("货币不相同");
        return new Money(this.amount.subtract(other.amount).toPlainString(), this.currency);
        // throw new UnsupportedOperationException("TODO: 实现 subtract");
    }

    public int compareTo(Money other) {
        // TODO: 先比 currency，再比 amount
        if (!this.currency.equals(other.currency)) return this.currency.compareTo(other.currency);
        return this.amount.compareTo(other.amount);
        // throw new UnsupportedOperationException("TODO: 实现 compareTo");
    }

    @Override
    public boolean equals(Object o) {
        // TODO: amount 用 compareTo == 0 判断相等
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return this.currency.equals(money.currency) && this.amount.equals(money.amount);
        // throw new UnsupportedOperationException("TODO: 实现 equals");
    }

    @Override
    public int hashCode() {
        // TODO
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        // TODO: 如 "128.00 CNY"
        return amount.toPlainString() + " " + currency;
        // throw new UnsupportedOperationException("TODO: 实现 toString");
    }

    public static void main(String[] args) {
        Money a = new Money("10.00", "CNY");
        Money b = new Money("5.50", "CNY");
        Money sum = a.add(b);

        check("15.50", sum.getAmount().toPlainString());
        check("CNY", sum.getCurrency());
        check(true, a.equals(new Money("10.00", "CNY")));
        check(false, a.equals(b));

        try {
            a.add(new Money("1.00", "USD"));
            throw new AssertionError("不同币种应抛异常");
        } catch (IllegalArgumentException ignored) {
        }

        System.out.println(a + " + " + b + " = " + sum);
        System.out.println("Money: 全部通过 ✓");
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }

    private static void check(boolean expected, boolean actual) {
        if (expected != actual) {
            throw new AssertionError("期望 " + expected + "，实际 " + actual);
        }
    }
}
