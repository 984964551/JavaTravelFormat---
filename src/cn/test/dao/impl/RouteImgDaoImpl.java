package cn.test.dao.impl;

import cn.test.dao.RouteImgDao;
import cn.test.domain.RouteImg;
import cn.test.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List findRouteImg(int rid) {
        String sql="select * from tab_route_img where rid=?";
        List<RouteImg> imgList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return imgList;
    }
}
