package cn.test.dao.impl;

import cn.test.dao.UserDao;
import cn.test.domain.User;
import cn.test.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImp implements UserDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findUserByUsername(String username) {
        User user=null;
        String sql="select * from tab_user where username=?";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){

        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql="insert into tab_user value(null,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public User findUserByCode(String code) {
        User user=null;
        try {
            String sql="select * from tab_user where code=?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }catch (Exception e){

        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status = 'Y' where uid=?";
        jdbcTemplate.update(sql,user.getUid());
    }

    @Override
    public User findUserByUP(String username,String password) {
        User user=null;
        String sql="select * from tab_user where username=? and password=?";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (Exception e){

        }
        return user;
    }
}
