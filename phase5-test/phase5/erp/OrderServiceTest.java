package phase5.erp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import phase4.exception.BusinessException;
import phase5.erp.discount.NoDiscountStrategy;
import phase5.erp.model.Order;
import phase5.erp.model.Product;
import phase5.erp.repository.OrderRepository;
import phase5.erp.repository.ProductRepository;
import phase5.erp.service.InventoryService;
import phase5.erp.service.OrderService;
import phase5.erp.service.ProductService;

/** ERP 订单服务单元测试 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    private ProductService productService;
    private OrderService orderService;

    private final Path productsFile = Path.of("test-products.json");
    private final Path ordersFile = Path.of("test-orders.json");

    @BeforeEach
    void setUp() {
        Map<String, Product> products = new HashMap<>();
        products.put("P001", new Product("P001", "机械键盘", 399.0, 10));
        when(productRepository.load(productsFile)).thenReturn(products);
        when(orderRepository.load(ordersFile)).thenReturn(java.util.List.of());

        productService = new ProductService(productRepository, productsFile);
        InventoryService inventoryService = new InventoryService(productService);
        orderService = new OrderService(
                productService, inventoryService, orderRepository, ordersFile);
    }

    @Test
    void createOrder_成功_扣库存并计算金额() {
        Order order = orderService.createOrder("O001", "P001", 1, new NoDiscountStrategy());

        assertEquals(399.0, order.getTotalAmount());
        assertEquals(9, productService.getProduct("P001").getStock());
        verify(orderRepository).save(eq(ordersFile), any());
    }

    @Test
    void createOrder_库存不足_抛INSUFFICIENT_STOCK() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> orderService.createOrder("O002", "P001", 100, new NoDiscountStrategy()));

        assertEquals("INSUFFICIENT_STOCK", ex.getErrorCode());
    }
}
