package phase5.patterns.decorator;

/**
 * 装饰器 — 在不改原类的前提下增强功能
 *
 * 场景：给通知加前缀、加密、重试… 可层层包装。
 * Java IO 的 BufferedInputStream 就是装饰器。
 */
public class PrefixNotifier implements Notifier {

    private final Notifier delegate;
    private final String prefix;

    public PrefixNotifier(Notifier delegate, String prefix) {
        this.delegate = delegate;
        this.prefix = prefix;
    }

    @Override
    public void send(String message) {
        delegate.send(prefix + message);
    }
}
