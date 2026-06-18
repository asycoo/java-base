package phase3.concurrency;

/**
 * 实操 3.1 - 线程不安全计数
 *
 * 10 个线程，每个对 count 累加 2000 次，期望 20000。
 * 运行多次，观察结果往往远小于 20000。
 *
 * 原因：count++ 不是原子操作（读-改-写三步可被其他线程打断）
 */
public class CounterUnsafe {

    private static int count = 0;
    private static final int THREADS = 10;
    private static final int PER_THREAD = 2000;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < PER_THREAD; j++) {
                    count++;  // 线程不安全！
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();  // 等待所有线程结束
        }

        System.out.println("期望: " + (THREADS * PER_THREAD));
        System.out.println("实际: " + count);
        if (count == THREADS * PER_THREAD) {
            System.out.println("碰巧正确（多运行几次通常会错）");
        } else {
            System.out.println("线程不安全：丢失了 " + (THREADS * PER_THREAD - count) + " 次更新");
        }
    }
}
