package cn.test.dao.impl;

import cn.test.dao.RouteDao;
import cn.test.domain.Route;
import cn.test.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int totalCount(int cid, String rname) {
//        String sql="select count(*) from tab_route where cid=?";
//        return  jdbcTemplate.queryForObject(sql, Integer.class, cid);
        String sql="select count(*) from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        //创建一个list集合来装条件
        List list=new ArrayList();
        //判断cid是否为空
        if (cid!=0){
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        //判断rname是否为空
        if (rname != null && rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ?");
            list.add("%"+ rname +"%");
        }
        sql=sb.toString();
        return jdbcTemplate.queryForObject(sql, Integer.class, list.toArray());
    }

    @Override
    public List<Route> findBypage(int cid, int start, int pageSize, String rname) {
        String sql="select * from tab_route where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        //创建一个list集合来装条件
        List list=new ArrayList();
        //判断cid是否为空
        if (cid!=0){
            sb.append(" and cid = ?");
            list.add(cid);
        }
        //判断rname是否为空
        if (rname!=null&&rname.length()>0&&!"null".equals(rname)){
            sb.append(" and rname like ? ");
            list.add("%" +rname+ "%");
        }
        sb.append(" limit ? , ?");
        sql=sb.toString();
        list.add(start);
        list.add(pageSize);
        List<Route> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
        return query;
    }

    @Override
    public Route findone(int rid) {
        String sql="select * from tab_route where rid=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
