package day3.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 实操 3.2 - 图书 CSV 持久化
 *
 * 要求：
 * - load：文件不存在 → 返回空 Map（不抛异常）
 * - save：写入表头 + 每本书一行 CSV
 * - 使用 Files.readAllLines / Files.write
 */
public class BookRepository {

    private static final String HEADER = "id,title,author,price,available";

    public Map<String, Book> load(Path path) {
        // TODO: 读文件 → 跳过表头 → 每行 Book.fromCsvLine → 放入 Map
        Map<String, Book> result = new HashMap<>();
        try {
            if (!Files.exists(path)) {
                return result;
            }
            List<String> lines = Files.readAllLines(path);
            if (lines.size() <= 1) {
                return result;
            }
            for (String line : lines.subList(1, lines.size())) {
                if (line.isBlank()) continue;
                Book book = Book.fromCsvLine(line);
                result.put(book.getId(), book);
            }
        } catch (IOException e) {
            return new HashMap<>();
        }
        return result;
        // throw new UnsupportedOperationException("TODO: 实现 load");
    }

    public void save(Path path, Map<String, Book> books) {
        // TODO: 组装 lines（表头 + toCsvLine）→ Files.write
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Book book : books.values()) {
            lines.add(book.toCsvLine());
        }
        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save books to file", e);
        }
        // throw new UnsupportedOperationException("TODO: 实现 save");
    }
}
