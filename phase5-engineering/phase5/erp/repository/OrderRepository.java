package phase5.erp.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import phase4.exception.InfrastructureException;
import phase5.erp.model.Order;
import phase5.erp.model.OrderItem;

/**
 * 订单 JSON 持久化
 */
public class OrderRepository {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);

    public List<Order> load(Path path) {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            List<OrderRecord> records = MAPPER.readValue(path.toFile(), new TypeReference<>() {});
            List<Order> result = new ArrayList<>();
            for (OrderRecord r : records) {
                Order order = new Order(r.id());
                for (ItemRecord item : r.items()) {
                    order.addItem(new OrderItem(item.productId(), item.quantity(), item.unitPrice()));
                }
                order.applyDiscount(r.discountRate());
                order.finalizeTotal(r.totalAmount());
                result.add(order);
            }
            return result;
        } catch (IOException e) {
            throw new InfrastructureException("FILE_READ_ERROR", "加载订单失败: " + path, e);
        }
    }

    public void save(Path path, List<Order> orders) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            List<OrderRecord> records = new ArrayList<>();
            for (Order order : orders) {
                List<ItemRecord> items = order.getItems().stream()
                        .map(i -> new ItemRecord(i.productId(), i.quantity(), i.unitPrice()))
                        .toList();
                records.add(new OrderRecord(
                        order.getId(), items, order.getDiscountRate(), order.getTotalAmount()));
            }
            MAPPER.writeValue(path.toFile(), records);
        } catch (IOException e) {
            throw new InfrastructureException("FILE_WRITE_ERROR", "保存订单失败: " + path, e);
        }
    }

    record OrderRecord(String id, List<ItemRecord> items, double discountRate, double totalAmount) {}

    record ItemRecord(String productId, int quantity, double unitPrice) {}
}
