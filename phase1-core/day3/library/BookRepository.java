package day3.library;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import phase4.exception.InfrastructureException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图书 JSON 持久化（v3）
 *
 * 格式：JSON 数组 [{ id, title, author, price, available }, ...]
 */
public class BookRepository {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public Map<String, Book> load(Path path) {
        if (!Files.exists(path)) {
            return new HashMap<>();
        }
        try {
            List<Book> list = MAPPER.readValue(path.toFile(), new TypeReference<List<Book>>() {});
            Map<String, Book> result = new HashMap<>();
            for (Book book : list) {
                result.put(book.getId(), book);
            }
            return result;
        } catch (IOException e) {
            throw new InfrastructureException("FILE_READ_ERROR", "加载图书数据失败: " + path, e);
        }
    }

    public void save(Path path, Map<String, Book> books) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            List<Book> list = new ArrayList<>(books.values());
            MAPPER.writeValue(path.toFile(), list);
        } catch (IOException e) {
            throw new InfrastructureException("FILE_WRITE_ERROR", "保存图书数据失败: " + path, e);
        }
    }
}
