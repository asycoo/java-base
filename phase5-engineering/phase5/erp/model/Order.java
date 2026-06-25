package phase5.erp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 */
public class Order {

    private final String id;
    private final List<OrderItem> items = new ArrayList<>();
    private final LocalDateTime createdAt;
    private double discountRate;
    private double totalAmount;

    public Order(String id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public List<OrderItem> getItems() { return List.copyOf(items); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public double getDiscountRate() { return discountRate; }
    public double getTotalAmount() { return totalAmount; }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void applyDiscount(double rate) {
        this.discountRate = rate;
    }

    public void finalizeTotal(double amount) {
        this.totalAmount = amount;
    }

    public double itemsSubtotal() {
        return items.stream().mapToDouble(OrderItem::subtotal).sum();
    }
}
