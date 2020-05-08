package cn.test.service;

import cn.test.domain.PageBean;
import cn.test.domain.Route;

public interface RouteService {
    //查询分页的数据内容
    public PageBean pageQuery(int cid, int currentpage, int pagesize, String rname);
    //查询具体某个线路的详情
    public Route findone(String rid);
}
