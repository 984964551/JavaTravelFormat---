package cn.test.service.impl;

import cn.test.dao.RouteDao;
import cn.test.dao.RouteImgDao;
import cn.test.dao.SellerDao;
import cn.test.dao.impl.RouteDaoImpl;
import cn.test.dao.impl.RouteImgDaoImpl;
import cn.test.dao.impl.SellerDaoImpl;
import cn.test.domain.PageBean;
import cn.test.domain.Route;
import cn.test.domain.Seller;
import cn.test.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao=new RouteDaoImpl();
    RouteImgDao routeImgDao=new RouteImgDaoImpl();
    SellerDao sellerDao=new SellerDaoImpl();
    @Override
    public PageBean pageQuery(int cid, int currentpage, int pagesize, String rname) {
        //创建PageBean对象
        PageBean<Route> pageBean=new PageBean();
        //设置当前页码和每页显示条数
        pageBean.setCurrentPage(currentpage);
        pageBean.setPageSize(pagesize);
        //根据cid查询总记录数，存入PageBean对象
        int totalCount = routeDao.totalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //计算总页数
        int totalPage=(totalCount%pagesize==0) ? (totalCount/pagesize) : (totalCount/pagesize+1);
        pageBean.setTotalpage(totalPage);
        //计算开始分页查询的记录
        int start=(currentpage-1)*pagesize;
        //调用Dao方法查询分页数据
        List<Route> list = routeDao.findBypage(cid, start, pagesize,rname);
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public Route findone(String rid) {
        //根据rid查询线路
        Route route = routeDao.findone(Integer.parseInt(rid));
        //根据rid查询图片集合
        List routeImg = routeImgDao.findRouteImg(route.getRid());
        //根据sid查询商家信息
        Seller seller = sellerDao.findSeller(route.getSid());
        route.setRouteImgList(routeImg);
        route.setSeller(seller);
        return route;
    }
}
