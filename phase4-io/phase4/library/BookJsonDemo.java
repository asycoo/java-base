package phase4.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 实操 4.2 - Jackson JSON 序列化
 *
 * 要求：
 * - toJson / fromJson 读写 BookRecord
 * - 理解 @JsonProperty、@JsonIgnore（Day 4 可给 day3.library.Book 加注解）
 */
public class BookJsonDemo {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) throws Exception {
        BookRecord book = new BookRecord("B001", "Java 核心技术", "Horstmann", 128.0, true);

        // TODO: 实现 toJson / fromJson 后取消注释
        String json = toJson(book);
        System.out.println("序列化:\n" + json);
        BookRecord parsed = fromJson(json);
        System.out.println("反序列化: " + parsed);

        // throw new UnsupportedOperationException("TODO: 实现 toJson / fromJson");
    }

    public static String toJson(BookRecord book) throws Exception {
        // TODO: MAPPER.writeValueAsString(book)
        return MAPPER.writeValueAsString(book);
    }

    public static BookRecord fromJson(String json) throws Exception {
        // TODO: MAPPER.readValue(json, BookRecord.class)
        return MAPPER.readValue(json, BookRecord.class);
    }

    /** 用于 JSON 演示的简单 DTO，后续可迁移到 day3.library.Book */
    public record BookRecord(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("author") String author,
            @JsonProperty("price") double price,
            @JsonIgnore() boolean available
    ) {}
}
