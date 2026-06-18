package phase3.concurrency;

/**
 * 实操 3.1 - synchronized 线程安全计数
 *
 * TODO: 用 synchronized 保护 count++，使结果稳定为 20000
 *
 * 方案 A：synchronized 方法 increment()
 * 方案 B：synchronized (lock) { count++; }
 */
public class CounterSynchronized {

    private static int count = 0;
    private static final Object lock = new Object();
    private static final int THREADS = 10;
    private static final int PER_THREAD = 2000;

    private static void increment() {
        // TODO: synchronized 保护 count++
        synchronized (lock) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < PER_THREAD; j++) {
                    increment();
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        int expected = THREADS * PER_THREAD;
        if (count != expected) {
            throw new AssertionError("期望 " + expected + "，实际 " + count);
        }
        System.out.println("CounterSynchronized: 全部通过 ✓ count=" + count);
    }
}
