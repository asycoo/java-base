package phase5.erp.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import phase4.exception.InfrastructureException;
import phase5.erp.model.Product;

/**
 * 商品 JSON 持久化
 *
 * TODO: 理解实现后，可对照 day3.library.BookRepository 复习
 */
public class ProductRepository {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public Map<String, Product> load(Path path) {
        if (!Files.exists(path)) {
            return new HashMap<>();
        }
        try {
            List<ProductRecord> records = MAPPER.readValue(path.toFile(), new TypeReference<>() {});
            Map<String, Product> result = new HashMap<>();
            for (ProductRecord r : records) {
                result.put(r.id(), new Product(r.id(), r.name(), r.price(), r.stock()));
            }
            return result;
        } catch (IOException e) {
            throw new InfrastructureException("FILE_READ_ERROR", "加载商品失败: " + path, e);
        }
    }

    public void save(Path path, Map<String, Product> products) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            List<ProductRecord> records = new ArrayList<>();
            for (Product p : products.values()) {
                records.add(new ProductRecord(p.getId(), p.getName(), p.getPrice(), p.getStock()));
            }
            MAPPER.writeValue(path.toFile(), records);
        } catch (IOException e) {
            throw new InfrastructureException("FILE_WRITE_ERROR", "保存商品失败: " + path, e);
        }
    }

    /** Jackson 序列化用 */
    record ProductRecord(String id, String name, double price, int stock) {}
}
