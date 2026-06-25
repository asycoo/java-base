package phase5.erp.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import phase4.exception.BusinessException;
import phase4.exception.InfrastructureException;
import phase4.exception.ValidationException;
import phase5.erp.discount.DiscountFactory;
import phase5.erp.model.Order;
import phase5.erp.model.Product;
import phase5.erp.repository.OrderRepository;
import phase5.erp.repository.ProductRepository;
import phase5.erp.service.InventoryService;
import phase5.erp.service.OrderService;
import phase5.erp.service.ProductService;

/**
 * 小型 ERP 控制台 — 阶段项目 v1
 *
 * 模块：商品 / 库存 / 订单
 * 技术：JSON 持久化 + SLF4J 日志 + 统一异常 + 折扣策略模式
 */
public class ErpMain {

    private static final Path DATA_DIR = locateDataDir();

    static Path locateDataDir() {
        Path[] candidates = {
                Path.of("phase5-engineering/phase5/erp/data"),
                Path.of("phase5/erp/data")
        };
        for (Path path : candidates) {
            if (Files.exists(path)) {
                return path;
            }
        }
        return Path.of("phase5-engineering/phase5/erp/data");
    }

    public static void main(String[] args) {
        ProductRepository productRepo = new ProductRepository();
        OrderRepository orderRepo = new OrderRepository();
        Path productsFile = DATA_DIR.resolve("products.json");
        Path ordersFile = DATA_DIR.resolve("orders.json");

        ProductService productService = new ProductService(productRepo, productsFile);
        InventoryService inventoryService = new InventoryService(productService);
        OrderService orderService = new OrderService(
                productService, inventoryService, orderRepo, ordersFile);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== 小型 ERP v1 ===");

        while (true) {
            printMenu();
            System.out.print("请选择: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> listProducts(productService);
                    case "2" -> stockIn(inventoryService, scanner);
                    case "3" -> createOrder(orderService, scanner);
                    case "4" -> listOrders(orderService);
                    case "0" -> {
                        productService.save();
                        System.out.println("再见！");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("无效选项");
                }
            } catch (ValidationException e) {
                System.out.println("参数错误: " + e.getMessage());
            } catch (BusinessException e) {
                System.out.printf("[%s] %s%n", e.getErrorCode(), e.getMessage());
            } catch (InfrastructureException e) {
                System.out.printf("系统错误 [%s]: %s%n", e.getErrorCode(), e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("请输入合法数字");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. 商品列表");
        System.out.println("2. 入库");
        System.out.println("3. 下单（可选 none/bulk 折扣）");
        System.out.println("4. 订单列表");
        System.out.println("0. 退出");
    }

    private static void listProducts(ProductService service) {
        for (Product p : service.listProducts()) {
            System.out.println(p);
        }
    }

    private static void stockIn(InventoryService inventory, Scanner scanner) {
        System.out.print("商品 ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("入库数量: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        inventory.stockIn(id, qty);
        System.out.println("入库成功");
    }

    private static void createOrder(OrderService orderService, Scanner scanner) {
        System.out.print("订单 ID: ");
        String orderId = scanner.nextLine().trim();
        System.out.print("商品 ID: ");
        String productId = scanner.nextLine().trim();
        System.out.print("数量: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("折扣类型(none/bulk): ");
        String discountType = scanner.nextLine().trim();
        if (discountType.isEmpty()) {
            discountType = "none";
        }
        Order order = orderService.createOrder(
                orderId, productId, qty, DiscountFactory.create(discountType));
        System.out.printf("下单成功 实付: ¥%.2f%n", order.getTotalAmount());
    }

    private static void listOrders(OrderService orderService) {
        for (Order order : orderService.listOrders()) {
            System.out.printf("订单 %s 实付 ¥%.2f 折扣 %.0f%%%n",
                    order.getId(), order.getTotalAmount(), order.getDiscountRate() * 100);
        }
    }
}
