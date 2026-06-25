package phase5.payment;

/** 银行转账 */
public class BankStrategy implements PaymentStrategy {

    @Override
    public String channel() {
        return "bank";
    }

    @Override
    public String pay(double amount) {
        System.out.println("银行转账: " + amount);
        return "bank_" + amount;
        // throw new UnsupportedOperationException("TODO: 实现 BankStrategy.pay");
    }
}
