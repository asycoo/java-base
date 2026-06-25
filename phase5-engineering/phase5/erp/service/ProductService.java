package phase5.erp.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phase4.exception.BusinessException;
import phase4.exception.ValidationException;
import phase5.erp.model.Product;
import phase5.erp.repository.ProductRepository;

/**
 * 商品服务
 */
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;
    private final Path dataFile;
    private final Map<String, Product> products;

    public ProductService(ProductRepository repository, Path dataFile) {
        this.repository = repository;
        this.dataFile = dataFile;
        this.products = repository.load(dataFile);
        log.info("加载商品 {} 个", products.size());
    }

    public List<Product> listProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProduct(String id) {
        Product product = products.get(id);
        if (product == null) {
            throw new BusinessException("PRODUCT_NOT_FOUND", "商品不存在: " + id);
        }
        return product;
    }

    public void addProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new ValidationException("商品名不能为空");
        }
        products.put(product.getId(), product);
        log.info("添加商品: {}", product.getId());
    }

    public void save() {
        repository.save(dataFile, products);
        log.info("商品已保存到 {}", dataFile);
    }

    Map<String, Product> productsInternal() {
        return products;
    }
}
