package phase3.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实操 3.1 - AtomicInteger 线程安全计数
 *
 * TODO: 用 AtomicInteger 替换 int count，使用 incrementAndGet() 或 getAndIncrement()
 */
public class CounterAtomic {

    // TODO: 改成 AtomicInteger
    private static AtomicInteger count = new AtomicInteger(0);
    private static final int THREADS = 10;
    private static final int PER_THREAD = 2000;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < PER_THREAD; j++) {
                    // TODO: 原子自增
                    count.incrementAndGet();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        int expected = THREADS * PER_THREAD;
        if (count.get() != expected) {
            throw new AssertionError("期望 " + expected + "，实际 " + count);
        }
        System.out.println("CounterAtomic: 全部通过 ✓ count=" + count);
    }
}
