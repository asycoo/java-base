package phase3.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 实操 3.2 - 生产者-消费者（BlockingQueue）
 *
 * 1 个生产者往队列放 1~20，2 个消费者取出并打印，队列满/空时自动阻塞。
 *
 * TODO: 实现 producer 和 consumer 的 Runnable
 */
public class ProducerConsumer {

    private static final int CAPACITY = 5;
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(CAPACITY);
    private static final int POISON = -1;

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(() -> {
            // TODO: for i 1..20，queue.put(i)，最后 put(POISON) 结束
            for (int i = 1; i <= 20; i++) {
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                queue.put(POISON);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "producer");

        Thread consumer1 = new Thread(() -> consume("C1"), "consumer-1");
        Thread consumer2 = new Thread(() -> consume("C2"), "consumer-2");

        producer.start();
        consumer1.start();
        consumer2.start();

        producer.join();
        consumer1.join();
        consumer2.join();
        System.out.println("ProducerConsumer: 完成 ✓");
    }

    private static void consume(String name) {
        // TODO: while true { int n = queue.take(); if (n == POISON) { queue.put(POISON); break; } print }
        while (true) {
            try {
                int n = queue.take();
                if (n == POISON) {
                    queue.put(POISON);
                    break;
                }
                System.out.println(name + " 消费 " + n);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
