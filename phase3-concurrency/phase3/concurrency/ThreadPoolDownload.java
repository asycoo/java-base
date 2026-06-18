package phase3.concurrency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 实操 3.3 - 固定大小线程池模拟下载
 *
 * 10 个下载任务，线程池大小 3，统计总耗时。
 */
public class ThreadPoolDownload {

    private static final int TASKS = 10;
    private static final int POOL_SIZE = 3;
    private static final int DOWNLOAD_MS = 500;

    public static void main(String[] args) {
        List<String> urls = IntStream.rangeClosed(1, TASKS)
                .mapToObj(i -> "https://example.com/file" + i)
                .toList();

        long start = System.currentTimeMillis();

        // try-with-resources：close 时会 shutdown 并等待所有任务完成（Java 19+）
        try (ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE)) {
            for (String url : urls) {
                pool.submit(() -> download(url));
            }
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("完成 %d 个任务，耗时 %d ms（%d 线程并行）%n", TASKS, elapsed, POOL_SIZE);
        System.out.println("ThreadPoolDownload: 完成 ✓");
    }

    static void download(String url) {
        try {
            System.out.println(Thread.currentThread().getName() + " 下载 " + url);
            Thread.sleep(DOWNLOAD_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
