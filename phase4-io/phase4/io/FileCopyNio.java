package phase4.io;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * 实操 4.1b - NIO 文件拷贝
 *
 * 要求：
 * - 使用 FileChannel.open 打开源和目标
 * - 用 transferTo 或 transferFrom 拷贝（零拷贝思路）
 * - try-with-resources 关闭 Channel
 */
public class FileCopyNio {

    public static void copy(Path source, Path target) throws IOException {
        // TODO: FileChannel + transferTo
        try (FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ);
             FileChannel targetChannel = FileChannel.open(
                     target,
                     StandardOpenOption.WRITE,
                     StandardOpenOption.CREATE,
                     StandardOpenOption.TRUNCATE_EXISTING)) {
            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        }
        // throw new UnsupportedOperationException("TODO: 实现 NIO 拷贝");
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("用法: java ... FileCopyNio <源文件> <目标文件>");
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
        System.out.printf("NIO 拷贝完成: %s → %s，耗时 %d ms%n", src, dest, ms);
    }
}
