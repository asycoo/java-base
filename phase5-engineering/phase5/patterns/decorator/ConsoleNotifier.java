package phase5.patterns.decorator;

/** 基础实现：控制台通知 */
public class ConsoleNotifier implements Notifier {

    @Override
    public void send(String message) {
        System.out.print(message);
    }
}
