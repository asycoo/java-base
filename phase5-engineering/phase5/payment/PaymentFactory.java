package phase5.payment;

import java.util.Map;

/**
 * 工厂模式 — 根据渠道名创建支付策略
 */
public class PaymentFactory {

    private static final Map<String, PaymentStrategy> STRATEGIES = Map.of(
            "alipay", new AlipayStrategy(),
            "wechat", new WechatStrategy(),
            "bank", new BankStrategy()
    );

    public static PaymentStrategy create(String channel) {
        PaymentStrategy strategy = STRATEGIES.get(channel.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付渠道: " + channel);
        }
        return strategy;
    }
}
