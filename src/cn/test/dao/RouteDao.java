package cn.test.dao;

import cn.test.domain.Route;

import java.util.List;

public interface RouteDao {
    //查询总记录数
    public  int totalCount(int cid, String rname);
    //查询每页的数据
    public List<Route> findBypage(int cid, int start, int pageSize, String rname);
    //查询具体线路
    public Route findone(int rid);
}
