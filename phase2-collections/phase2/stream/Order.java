package phase2.stream;

import java.time.LocalDate;

/**
 * 订单模型
 */
public record Order(String userId, double amount, LocalDate createdAt) {
}
