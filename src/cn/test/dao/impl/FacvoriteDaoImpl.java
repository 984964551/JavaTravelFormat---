package cn.test.dao.impl;

import cn.test.dao.FacvoriteDao;
import cn.test.domain.Favorite;
import cn.test.domain.Route;
import cn.test.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FacvoriteDaoImpl implements FacvoriteDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findfavorite(int rid, int uid) {
        Favorite favorite=null;
        String sql="select * from tab_favorite where rid=? and uid=?";
        try {
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e){

        }
        return favorite;
    }

    @Override
    public void savefavorite(int rid, int uid) {
        String sql="insert into tab_favorite value(?,?,?)";
        Date date=new Date();
        jdbcTemplate.update(sql,rid,date,uid );
    }

    public List<Route> findfavoriterid(int uid){
        String sql="select rid from tab_favorite where uid=?";
        List<Route> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid);
        return list;
    }

    @Override
    public Route findallfavorite(int rid) {
        String sql="select * from tab_route where rid=?";
        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }
}
