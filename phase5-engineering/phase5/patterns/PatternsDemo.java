package phase5.patterns;

import phase5.erp.discount.DiscountFactory;
import phase5.patterns.decorator.ConsoleNotifier;
import phase5.patterns.decorator.Notifier;
import phase5.patterns.decorator.PrefixNotifier;
import phase5.patterns.observer.OrderSubject;
import phase5.patterns.observer.SmsListener;
import phase5.patterns.observer.StockListener;
import phase5.patterns.proxy.UserService;
import phase5.patterns.proxy.UserServiceImpl;
import phase5.patterns.proxy.UserServiceProxy;
import phase5.patterns.singleton.AppConfig;
import phase5.patterns.template.CsvExporter;
import phase5.patterns.template.DataExporter;
import phase5.patterns.template.JsonExporter;
import phase5.payment.PaymentContext;
import phase5.payment.PaymentFactory;

/**
 * 必会设计模式 — 一键演示（每段 5 行内看懂）
 *
 * mvn exec:java -Dexec.mainClass=phase5.patterns.PatternsDemo
 */
public class PatternsDemo {

    public static void main(String[] args) {
        demoSingleton();
        demoFactoryAndStrategy();
        demoTemplateMethod();
        demoObserver();
        demoDecorator();
        demoProxy();
        System.out.println("\nPatternsDemo: 全部演示完成 ✓");
    }

    static void demoSingleton() {
        System.out.println("=== 1. 单例 Singleton ===");
        AppConfig a = AppConfig.INSTANCE;
        AppConfig b = AppConfig.INSTANCE;
        System.out.println("同一实例? " + (a == b));
        System.out.println("appName=" + a.getAppName() + " maxRetry=" + a.getMaxRetry());
    }

    static void demoFactoryAndStrategy() {
        System.out.println("\n=== 2. 工厂 Factory + 3. 策略 Strategy ===");
        // 工厂：根据名字创建对象
        var payment = PaymentFactory.create("alipay");
        var context = new PaymentContext(payment);
        System.out.println("支付: " + context.pay(50));

        // 策略：同一接口，不同算法
        var discount = DiscountFactory.create("bulk");
        double subtotal = 250;
        System.out.printf("满减策略 rate=%.0f%% 实付=%.2f%n",
                discount.discountRate(subtotal) * 100,
                subtotal * (1 - discount.discountRate(subtotal)));
    }

    static void demoTemplateMethod() {
        System.out.println("\n=== 4. 模板方法 Template Method ===");
        DataExporter csv = new CsvExporter();
        DataExporter json = new JsonExporter();
        csv.export("hello world");
        json.export("hello world");
    }

    static void demoObserver() {
        System.out.println("\n=== 5. 观察者 Observer ===");
        OrderSubject subject = new OrderSubject();
        subject.subscribe(new SmsListener());
        subject.subscribe(new StockListener());
        subject.createOrder("O100", 399.0);
    }

    static void demoDecorator() {
        System.out.println("\n=== 6. 装饰器 Decorator ===");
        Notifier base = new ConsoleNotifier();
        Notifier decorated = new PrefixNotifier(base, "[ERP] ");
        decorated.send("系统启动\n");
    }

    static void demoProxy() {
        System.out.println("\n=== 7. 代理 Proxy ===");
        UserService service = new UserServiceProxy(new UserServiceImpl());
        service.login("张三");
    }
}
