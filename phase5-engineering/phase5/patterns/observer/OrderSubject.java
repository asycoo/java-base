package phase5.patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者 — 被观察的对象（Subject）
 *
 * 场景：下单后通知库存、短信、日志等多个模块。
 */
public class OrderSubject {

    private final List<OrderListener> listeners = new ArrayList<>();

    public void subscribe(OrderListener listener) {
        listeners.add(listener);
    }

    public void createOrder(String orderId, double amount) {
        System.out.println("创建订单: " + orderId + " 金额 " + amount);
        for (OrderListener listener : listeners) {
            listener.onOrderCreated(orderId, amount);
        }
    }
}
