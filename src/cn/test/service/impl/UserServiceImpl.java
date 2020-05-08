package cn.test.service.impl;

import cn.test.dao.UserDao;
import cn.test.dao.impl.UserDaoImp;
import cn.test.domain.User;
import cn.test.service.UserService;
import cn.test.util.MailUtils;
import cn.test.util.UuidUtil;

public class UserServiceImpl implements UserService {
    @Override
    public boolean regist(User user) {
        //调用dao方法来查询用户是否存在
        UserDao userDao = new UserDaoImp();
        User user1 = userDao.findUserByUsername(user.getUsername());
        //用户存在返回false
        if (user1 != null) {
            return false;
        }
        //用户不存在
        //给用户设置激活码
        user.setCode(UuidUtil.getUuid());
        //设置用户的激活状态
        user.setStatus("N");
        userDao.save(user);
        //激活邮件，发送邮件正文
        String content = "<a href='http://localhost/user/activeUserServlet?code=" + user.getCode() + "'>点击激活【安成之黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //根据激活码查询对象
        UserDao userDao = new UserDaoImp();
        User user = userDao.findUserByCode(code);
        if (user != null) {
            //修改对象的状态
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User find(User user) {
        //调用userdao方法来查找用户
        UserDao userDao = new UserDaoImp();
        User user1=null;
        try {
            user1 = userDao.findUserByUP(user.getUsername(),user.getPassword());
        }catch (Exception e){

        }
        return user1;
    }

    @Override
    public boolean status(User user) {
        //调用userdao方法来查找用户
        UserDao userDao=new UserDaoImp();
        User user1=userDao.findUserByUP(user.getUsername(),user.getPassword());
        //获取用户的激活状态
        if (user1.getStatus().equals("Y")){
            return true;
        }else {
            return false;
        }
    }
}


