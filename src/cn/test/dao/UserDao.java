package cn.test.dao;


import cn.test.domain.User;

public interface UserDao {
    //通过username查询该用户名是否已存在
    public User findUserByUsername(String username);
    //创建该用户
    public void save(User user);
    //根据激活码查找用户
    public User findUserByCode(String code);
    //修改用户的状态
    public void updateStatus(User user);
    //通过username何password查找用户
    User findUserByUP(String username,String password);
}
