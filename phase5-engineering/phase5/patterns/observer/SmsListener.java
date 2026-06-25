package phase5.patterns.observer;

/** 观察者实现：发短信 */
public class SmsListener implements OrderListener {

    @Override
    public void onOrderCreated(String orderId, double amount) {
        System.out.println("  [短信] 订单 " + orderId + " 已创建，金额 " + amount);
    }
}
