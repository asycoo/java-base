package phase5.payment;

/**
 * 支付上下文 — 运行时持有并切换策略
 */
public class PaymentContext {

    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public String pay(double amount) {
        return strategy.pay(amount);
    }

    public String currentChannel() {
        return strategy.channel();
    }
}
