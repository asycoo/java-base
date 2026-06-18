package phase3.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 实操 3.5 - OOM 实验
 *
 * 正常运行可能很久才 OOM。请用限制堆的方式运行：
 *
 *   java -Xms20m -Xmx20m -cp target/classes phase3.jvm.OomDemo
 *
 * 观察 OutOfMemoryError: Java heap space
 */
public class OomDemo {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        int i = 0;
        try {
            while (true) {
                // 每次分配 1MB
                list.add(new byte[1024 * 1024]);
                i++;
                if (i % 10 == 0) {
                    System.out.println("已分配 " + i + " MB");
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OOM 在第 " + i + " MB 时触发");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
            System.out.println("OomDemo: 实验完成 ✓");
        }
    }
}
