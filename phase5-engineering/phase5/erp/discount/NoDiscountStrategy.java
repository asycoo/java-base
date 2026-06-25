package phase5.erp.discount;

/** 无折扣 */
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public String name() {
        return "none";
    }

    @Override
    public double discountRate(double itemsSubtotal) {
        return 0.0;
    }
}
