package com.nowcoder.toutiao.model;
import org.springframework.stereotype.Component;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class HostHolder {    //存储用户，得到一次访问的用户
    private static ThreadLocal<User> users = new ThreadLocal<User>(); //线程用来解决多用户访问

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
