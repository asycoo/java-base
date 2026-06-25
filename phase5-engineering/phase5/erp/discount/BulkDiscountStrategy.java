package phase5.erp.discount;

/** 满 200 减 10% */
public class BulkDiscountStrategy implements DiscountStrategy {

    private static final double THRESHOLD = 200.0;
    private static final double RATE = 0.10;

    @Override
    public String name() {
        return "bulk";
    }

    @Override
    public double discountRate(double itemsSubtotal) {
        return itemsSubtotal >= THRESHOLD ? RATE : 0.0;
    }
}
