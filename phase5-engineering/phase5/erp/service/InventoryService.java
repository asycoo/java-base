package phase5.erp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phase4.exception.BusinessException;
import phase5.erp.model.Product;

/**
 * 库存服务 — 入库 / 出库
 */
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final ProductService productService;

    public InventoryService(ProductService productService) {
        this.productService = productService;
    }

    public void stockIn(String productId, int quantity) {
        if (quantity <= 0) {
            throw new BusinessException("INVALID_QUANTITY", "入库数量必须大于 0");
        }
        Product product = productService.getProduct(productId);
        product.setStock(product.getStock() + quantity);
        log.info("入库 product={} qty={} 当前库存={}", productId, quantity, product.getStock());
    }

    public void stockOut(String productId, int quantity) {
        if (quantity <= 0) {
            throw new BusinessException("INVALID_QUANTITY", "出库数量必须大于 0");
        }
        Product product = productService.getProduct(productId);
        if (product.getStock() < quantity) {
            throw new BusinessException("INSUFFICIENT_STOCK", "库存不足: " + product.getName());
        }
        product.setStock(product.getStock() - quantity);
        log.info("出库 product={} qty={} 剩余库存={}", productId, quantity, product.getStock());
    }
}
