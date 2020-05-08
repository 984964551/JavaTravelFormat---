package cn.test.service.impl;

import cn.test.dao.FacvoriteDao;
import cn.test.dao.impl.FacvoriteDaoImpl;
import cn.test.domain.Favorite;
import cn.test.domain.PageBean;
import cn.test.domain.Route;
import cn.test.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    FacvoriteDao facvoriteDao = new FacvoriteDaoImpl();

    @Override
    public boolean isfavorite(String rid, int uid) {
        Favorite favorite = null;
        try {
            favorite = facvoriteDao.findfavorite(Integer.parseInt(rid), uid);
        } catch (Exception e) {

        }
        if (favorite != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void savefavorite(String rid, int uid) {
        //调用dao方法存储用户喜欢
        facvoriteDao.savefavorite(Integer.parseInt(rid), uid);
    }

    @Override
    public List<Route> showfavorite(int uid) {
        //查出所有的线路的rid
        List<Route> list = facvoriteDao.findfavoriterid(uid);
        List<Route> routelist = new ArrayList<>();
        //for循环通过rid查找出所有的route
        for (Route rid : list) {
            Route route = facvoriteDao.findallfavorite(rid.getRid());
            routelist.add(route);
        }
        return routelist;
    }

    public PageBean<Route> showpagefavorite(int uid, int currentPage, int pageSize) {
        //查出所有的线路的rid
        List<Route> list = facvoriteDao.findfavoriterid(uid);
        List<Route> routes = new ArrayList<>();
        //创建PageBean对象
        PageBean<Route> pageBean = new PageBean<>();
        //计算总记录数
        int totalCount = list.size();
        pageBean.setTotalCount(totalCount);
        pageBean.setPageSize(pageSize);
        //计算totalPage
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pageBean.setTotalpage(totalPage);
        pageBean.setCurrentPage(currentPage);
        //计算分页的start
        int start = (currentPage - 1) * pageSize;
        //查询当前页的数据
        if (currentPage < totalPage) {
            for (int i = start; i < pageSize; i++) {
                Route route = facvoriteDao.findallfavorite(list.get(i).getRid());
                routes.add(route);
            }
        } else {
            for (int i = start; i < totalCount; i++) {
                Route route = facvoriteDao.findallfavorite(list.get(i).getRid());
                routes.add(route);
            }
        }
        pageBean.setList(routes);
        return pageBean;
    }
}