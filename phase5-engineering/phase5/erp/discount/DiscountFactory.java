package phase5.erp.discount;

import java.util.Map;

/** 折扣策略工厂 */
public class DiscountFactory {

    private static final Map<String, DiscountStrategy> STRATEGIES = Map.of(
            "none", new NoDiscountStrategy(),
            "bulk", new BulkDiscountStrategy()
    );

    public static DiscountStrategy create(String type) {
        DiscountStrategy strategy = STRATEGIES.get(type.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("未知折扣类型: " + type);
        }
        return strategy;
    }
}
