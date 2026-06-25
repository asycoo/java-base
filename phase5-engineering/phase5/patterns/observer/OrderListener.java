package phase5.patterns.observer;

/** 观察者 — 订单事件监听器 */
public interface OrderListener {

    void onOrderCreated(String orderId, double amount);
}
