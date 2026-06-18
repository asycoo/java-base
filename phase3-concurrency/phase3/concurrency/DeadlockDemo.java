package phase3.concurrency;

/**
 * 实操 3.4 - 死锁复现
 *
 * 运行后程序会卡住。另开终端：jps → jstack <pid> 查看死锁。
 *
 * 经典场景：线程1 先锁 A 再锁 B，线程2 先锁 B 再锁 A
 */
public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                sleep(100);
                synchronized (lockB) {
                    System.out.println("t1 拿到 A 和 B");
                }
            }
        }, "thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                sleep(100);
                synchronized (lockA) {
                    System.out.println("t2 拿到 B 和 A");
                }
            }
        }, "thread-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("不会执行到这里");
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
