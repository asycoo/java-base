package phase5.patterns.observer;

/** 观察者实现：扣库存 */
public class StockListener implements OrderListener {

    @Override
    public void onOrderCreated(String orderId, double amount) {
        System.out.println("  [库存] 订单 " + orderId + " 触发出库");
    }
}
