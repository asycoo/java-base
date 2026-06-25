package phase5.erp.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phase4.exception.BusinessException;
import phase5.erp.discount.DiscountStrategy;
import phase5.erp.model.Order;
import phase5.erp.model.OrderItem;
import phase5.erp.model.Product;
import phase5.erp.repository.OrderRepository;

/**
 * 订单服务 — 下单扣库存 + 折扣策略
 */
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    private final Path ordersFile;
    private final List<Order> orders = new ArrayList<>();

    public OrderService(
            ProductService productService,
            InventoryService inventoryService,
            OrderRepository orderRepository,
            Path ordersFile) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.orderRepository = orderRepository;
        this.ordersFile = ordersFile;
        orders.addAll(orderRepository.load(ordersFile));
        log.info("加载历史订单 {} 条", orders.size());
    }

    /**
     * 创建订单：校验库存 → 扣减 → 应用折扣 → 持久化
     */
    public Order createOrder(String orderId, String productId, int quantity, DiscountStrategy discount) {
        Product product = productService.getProduct(productId);
        if (quantity <= 0) {
            throw new BusinessException("INVALID_QUANTITY", "购买数量必须大于 0");
        }

        inventoryService.stockOut(productId, quantity);

        Order order = new Order(orderId);
        order.addItem(new OrderItem(productId, quantity, product.getPrice()));

        double subtotal = order.itemsSubtotal();
        double rate = discount.discountRate(subtotal);
        order.applyDiscount(rate);
        double total = subtotal * (1 - rate);
        order.finalizeTotal(total);

        orders.add(order);
        orderRepository.save(ordersFile, orders);
        productService.save();

        log.info("创建订单 {} 小计={} 折扣={}% 实付={}", orderId, subtotal, rate * 100, total);
        return order;
    }

    public List<Order> listOrders() {
        return List.copyOf(orders);
    }
}
