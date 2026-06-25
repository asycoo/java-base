package phase5.erp.discount;

/**
 * 折扣策略 — 策略模式（ERP 第 2 种设计模式，第 1 种见 payment）
 */
public interface DiscountStrategy {

    /** 策略名称 */
    String name();

    /** 返回折扣率 0.0~1.0，如 0.1 表示 9 折 */
    double discountRate(double itemsSubtotal);
}
