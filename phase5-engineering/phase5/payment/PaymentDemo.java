package phase5.payment;

/**
 * 实操 5.2 — 策略 + 工厂模式演示
 *
 * 要求：实现各 Strategy.pay() 后运行本类
 */
public class PaymentDemo {

    public static void main(String[] args) {
        double amount = 99.0;

        for (String channel : new String[]{"alipay", "wechat", "bank"}) {
            PaymentStrategy strategy = PaymentFactory.create(channel);
            PaymentContext context = new PaymentContext(strategy);
            String txnId = context.pay(amount);
            System.out.printf("[%s] 支付 %.2f 元，交易号: %s%n",
                    context.currentChannel(), amount, txnId);
        }

        System.out.println("PaymentDemo: 完成 ✓");
    }
}
