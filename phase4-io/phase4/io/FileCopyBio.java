package phase4.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 实操 4.1a - BIO 文件拷贝
 *
 * 要求：
 * - 使用 FileInputStream / FileOutputStream + 缓冲流
 * - try-with-resources 自动关闭
 * - 按 byte[] 分块读写，不要一次性读整个文件
 */
public class FileCopyBio {

    private static final int BUFFER_SIZE = 8192;

    public static void copy(Path source, Path target) throws IOException {
        // TODO: try-with-resources + BufferedInputStream/BufferedOutputStream
        // 循环 read → write 直到 -1
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source.toFile()));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target.toFile()))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("用法: java ... FileCopyBio <源文件> <目标文件>");
            System.out.println("示例: java -cp target/classes phase4.io.FileCopyBio /tmp/large.bin /tmp/out-bio.bin");
            return;
        }
        Path src = Path.of(args[0]);
        Path dest = Path.of(args[1]);
        if (!Files.exists(src)) {
            System.err.println("源文件不存在: " + src);
            return;
        }
        long start = System.nanoTime();
        copy(src, dest);
        long ms = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("BIO 拷贝完成: %s → %s，耗时 %d ms%n", src, dest, ms);
    }
}
