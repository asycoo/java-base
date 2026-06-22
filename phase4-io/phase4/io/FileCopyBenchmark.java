package phase4.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 实操 4.1c - BIO vs NIO 性能对比
 *
 * 自动生成 100MB 测试文件到 /tmp/java-base-large.bin，分别跑两种拷贝并对比耗时。
 */
public class FileCopyBenchmark {

    private static final int SIZE_MB = 100;
    private static final Path TEST_FILE = Path.of("/tmp/java-base-large.bin");

    public static void main(String[] args) throws IOException {
        ensureTestFile(TEST_FILE, SIZE_MB);

        Path bioOut = Path.of("/tmp/java-base-copy-bio.bin");
        Path nioOut = Path.of("/tmp/java-base-copy-nio.bin");

        long bioMs = time(() -> FileCopyBio.copy(TEST_FILE, bioOut));
        long nioMs = time(() -> FileCopyNio.copy(TEST_FILE, nioOut));

        System.out.println();
        System.out.printf("源文件: %d MB%n", SIZE_MB);
        System.out.printf("BIO: %d ms%n", bioMs);
        System.out.printf("NIO: %d ms%n", nioMs);
        System.out.println("FileCopyBenchmark: 完成 ✓");
    }

    static void ensureTestFile(Path path, int sizeMb) throws IOException {
        if (Files.exists(path) && Files.size(path) == (long) sizeMb * 1024 * 1024) {
            System.out.println("复用已有测试文件: " + path);
            return;
        }
        System.out.printf("生成 %d MB 测试文件: %s ...%n", sizeMb, path);
        byte[] chunk = new byte[8192];
        long total = (long) sizeMb * 1024 * 1024;
        try (var out = Files.newOutputStream(path)) {
            long written = 0;
            while (written < total) {
                int len = (int) Math.min(chunk.length, total - written);
                out.write(chunk, 0, len);
                written += len;
            }
        }
    }

    @FunctionalInterface
    interface IoTask {
        void run() throws IOException;
    }

    static long time(IoTask task) throws IOException {
        long start = System.nanoTime();
        task.run();
        return (System.nanoTime() - start) / 1_000_000;
    }
}
