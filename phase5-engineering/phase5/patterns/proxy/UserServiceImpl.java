package phase5.patterns.proxy;

/** 真实对象 */
public class UserServiceImpl implements UserService {

    @Override
    public void login(String username) {
        System.out.println("用户 " + username + " 登录成功");
    }
}
