package phase5.patterns.proxy;

/**
 * 静态代理 — 在调用前后加逻辑（日志、鉴权、事务）
 *
 * Spring AOP 本质是动态代理，思想相同。
 */
public class UserServiceProxy implements UserService {

    private final UserService target;

    public UserServiceProxy(UserService target) {
        this.target = target;
    }

    @Override
    public void login(String username) {
        System.out.println("[代理] 校验参数...");
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        System.out.println("[代理] 调用真实 login");
        target.login(username);
        System.out.println("[代理] 记录审计日志");
    }
}
