package phase5.payment;

/** 微信支付 */
public class WechatStrategy implements PaymentStrategy {

    @Override
    public String channel() {
        return "wechat";
    }

    @Override
    public String pay(double amount) {
        System.out.println("微信支付: " + amount);
        return "wechat_" + amount;
        // throw new UnsupportedOperationException("TODO: 实现 WechatStrategy.pay");
    }
}
