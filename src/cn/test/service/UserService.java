package cn.test.service;

import cn.test.domain.User;

public interface UserService {
    //判断用户名是否存在，不存在则创建该用户
    boolean regist(User user);
    //验证码激活
    boolean active(String code);
    //验证登录用户的账号密码
    User find(User user);
    //验证用户的激活状态
    boolean status(User user);
}
