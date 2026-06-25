package phase5.payment;

/** 支付宝 */
public class AlipayStrategy implements PaymentStrategy {

    @Override
    public String channel() {
        return "alipay";
    }

    @Override
    public String pay(double amount) {
        // TODO: 实现支付逻辑（模拟返回交易号即可）
        System.out.println("支付宝支付: " + amount);
        return "alipay_" + amount;
        // throw new UnsupportedOperationException("TODO: 实现 AlipayStrategy.pay");
    }
}
