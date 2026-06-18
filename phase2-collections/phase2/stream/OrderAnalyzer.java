package phase2.stream;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实操 2.3 - 订单分析系统（纯 Stream）
 *
 * 输入一批 Order，实现：
 * 1. totalByUser()      — 每用户总消费 Map<userId, sum>
 * 2. maxAmountByUser()  — 每用户最高单笔 Map<userId, maxAmount>
 * 3. dailySumLast7Days()— 近 7 天每日总消费 Map<date, sum>（含今天共 7 天）
 *
 * 业务逻辑禁止用 for / while / for-each，全部用 Stream。
 */
public class OrderAnalyzer {

    private final List<Order> orders;
    private final LocalDate today;

    public OrderAnalyzer(List<Order> orders, LocalDate today) {
        this.orders = orders;
        this.today = today;
    }

    public Map<String, Double> totalByUser() {
        // TODO: Collectors.groupingBy + summingDouble
        return orders.stream()
        .collect(Collectors.groupingBy(Order::userId, Collectors.summingDouble(Order::amount)));
        // throw new UnsupportedOperationException("TODO: 实现 totalByUser");
    }

    public Map<String, Double> maxAmountByUser() {
        // TODO: groupingBy + maxBy/comparing
        return orders.stream()
        .collect(Collectors.groupingBy(Order::userId, Collectors.maxBy(Comparator.comparingDouble(Order::amount))))
        .entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get().amount()));
        // throw new UnsupportedOperationException("TODO: 实现 maxAmountByUser");
    }

    public Map<LocalDate, Double> dailySumLast7Days() {
        // TODO: 过滤近 7 天 → 按日期分组求和
        return orders.stream()
        .filter(order -> order.createdAt().isAfter(today.minusDays(7)))
        .collect(Collectors.groupingBy(Order::createdAt, Collectors.summingDouble(Order::amount)));
        // throw new UnsupportedOperationException("TODO: 实现 dailySumLast7Days");
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2026, 6, 16);
        List<Order> orders = List.of(
                new Order("U1", 100, today),
                new Order("U1", 200, today.minusDays(1)),
                new Order("U2", 50, today),
                new Order("U2", 300, today.minusDays(3)),
                new Order("U1", 150, today.minusDays(10))  // 超过 7 天，不计入 daily
        );

        OrderAnalyzer analyzer = new OrderAnalyzer(orders, today);

        Map<String, Double> total = analyzer.totalByUser();
        check(450.0, total.get("U1"));
        check(350.0, total.get("U2"));

        Map<String, Double> max = analyzer.maxAmountByUser();
        check(200.0, max.get("U1"));
        check(300.0, max.get("U2"));

        Map<LocalDate, Double> daily = analyzer.dailySumLast7Days();
        check(150.0, daily.get(today));           // U1:100 + U2:50
        check(200.0, daily.get(today.minusDays(1)));
        check(300.0, daily.get(today.minusDays(3)));
        check(null, daily.get(today.minusDays(10))); // 不在近 7 天

        System.out.println("OrderAnalyzer: 全部通过 ✓");
    }

    private static void check(Double expected, Double actual) {
        if (expected == null && actual == null) return;
        if (expected != null && actual != null && Math.abs(expected - actual) < 0.001) return;
        throw new AssertionError("期望 " + expected + "，实际 " + actual);
    }
}
