package phase5.erp.model;

/**
 * 订单明细行
 */
public record OrderItem(String productId, int quantity, double unitPrice) {

    public double subtotal() {
        return unitPrice * quantity;
    }
}
