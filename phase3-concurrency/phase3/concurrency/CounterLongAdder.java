package phase3.concurrency;

import java.util.concurrent.atomic.LongAdder;

/**
 * 实操 3.1 - LongAdder 高并发计数（推荐）
 *
 * TODO: 用 LongAdder 实现，最后 sum() 获取结果
 *
 * LongAdder vs AtomicInteger：高并发下 LongAdder 分段累加，性能更好
 */
public class CounterLongAdder {

    // TODO: 改成 LongAdder
    private static LongAdder adder = new LongAdder();
    private static final int THREADS = 10;
    private static final int PER_THREAD = 2000;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < PER_THREAD; j++) {
                    // TODO: adder.increment()
                    adder.increment();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        int expected = THREADS * PER_THREAD;
        if (adder.sum() != expected) {
            throw new AssertionError("期望 " + expected + "，实际 " + adder.sum());
        }
        System.out.println("CounterLongAdder: 全部通过 ✓ count=" + adder.sum());
    }
}
