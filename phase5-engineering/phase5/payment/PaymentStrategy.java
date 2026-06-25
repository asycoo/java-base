package phase5.payment;

/**
 * 支付策略接口 — 策略模式
 */
public interface PaymentStrategy {

    /** 渠道名称，如 alipay / wechat / bank */
    String channel();

    /** 执行支付，返回交易号 */
    String pay(double amount);
}
