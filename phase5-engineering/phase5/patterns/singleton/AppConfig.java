package phase5.patterns.singleton;

/**
 * 单例 — 枚举写法（推荐，线程安全、防反射）
 *
 * 场景：全局唯一配置，如数据库 URL、开关。
 */
public enum AppConfig {

    INSTANCE;

    private final String appName = "java-base";
    private final int maxRetry = 3;

    public String getAppName() {
        return appName;
    }

    public int getMaxRetry() {
        return maxRetry;
    }
}
