package cn.test.service;

import cn.test.domain.PageBean;
import cn.test.domain.Route;

import java.util.List;

public interface FavoriteService {
    //用户是否收藏该线路
    boolean isfavorite(String rid, int uid);
    //存储用户的收藏
    void savefavorite(String rid, int uid);
    //查询用户所有的喜欢
    public List<Route> showfavorite(int uid);
    //查询用户喜欢的分页数据
    public PageBean<Route> showpagefavorite(int uid, int currentPage, int pageSize);
}
