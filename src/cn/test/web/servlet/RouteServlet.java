package cn.test.web.servlet;

import cn.test.domain.PageBean;
import cn.test.domain.Route;
import cn.test.domain.User;
import cn.test.service.FavoriteService;
import cn.test.service.RouteService;
import cn.test.service.impl.FavoriteServiceImpl;
import cn.test.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    //查询分页展示的数据
    public void pageQueryServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String currentPagestr = request.getParameter("currentPage");
        String pageSizestr = request.getParameter("pageSize");
        String cidstr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        //处理参数
        int cid = 0;
        if (cidstr != null && cidstr.length() > 0 && !"null".equals(cidstr)) {
            //将string数据变成int
            cid = Integer.parseInt(cidstr);
        }

        int currentPage = 0;
        if (currentPagestr != null && currentPagestr.length() > 0) {
            //将string数据变成int
            currentPage = Integer.parseInt(currentPagestr);
        } else {
            //默认第一次给当前页面赋值为1
            currentPage = 1;
        }

        int pageSize = 0;
        if (pageSizestr != null && pageSizestr.length() > 0) {
            //将string数据变成int
            pageSize = Integer.parseInt(pageSizestr);
        } else {
            //默认第一次给当前页面赋值为1
            pageSize = 5;
        }
        //调用RouteService方法
        PageBean pageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
        //序列化对象json返回客户端
        witevalue(pageBean, response);
    }

    //查询某个旅游线路的具体详情
    public void findoneServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收rid数据
        String rid = request.getParameter("rid");
        //调用Service方法
        Route route = routeService.findone(rid);
        //序列化json，传回客户端
        witevalue(route, response);
    }

    //查询用户是否收藏
    public void isfavoriteServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收前台的rid
        String rid = request.getParameter("ridf");
        //接收用户登录时存在session中的user
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if (user == null) {
            //用户未登录
            uid = 0;
        } else {
            uid = user.getUid();
        }
        //调用Service方法
        boolean flag = favoriteService.isfavorite(rid, uid);
        //序列化json，传回客户端
        witevalue(flag, response);
    }

    //存储用户收藏
    public void savefavoriteServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取
        //接收前台的rid
        String rid = request.getParameter("ridf");
        //接收用户登录时存在session中的user
        User user = (User) request.getSession().getAttribute("user");
        boolean flag = false;
        if (user != null) {
            int uid = user.getUid();
            //调用Service方法储存收藏的线路
            favoriteService.savefavorite(rid, uid);
            flag = true;
        }
        //序列化json
        ObjectMapper objectMapper=new ObjectMapper();
        String json = objectMapper.writeValueAsString(flag);
        //设置编码
        response.setContentType("application/json;charset=utf-8");
        //响应给客户端
        response.getWriter().write(json);
    }

    //
    public void showmyfavoriteServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取参数
        String currentPagestrstr = request.getParameter("currentPage");
        //收藏页默认一页8张图
        int pageSize=100;
        int currentPage=0;
        if (currentPagestrstr!=null && currentPagestrstr.length()>0 &&!"null".equals(currentPagestrstr)){
            currentPage=Integer.parseInt(currentPagestrstr);
        }else {
            //默认开始时页码未第一页
            currentPage=1;
        }
        //获取用户登录时存在session中的user
        User user = (User) request.getSession().getAttribute("user");
        int uid=0;
        if (user!=null&&!"null".equals(user)){
            uid=user.getUid();
        }
//        //调用Service方法来查询用户收藏线路的rid
//        List<Route> list = favoriteService.showfavorite(uid);
//        //序列化json,传回客户端
//        witevalue(list,response );
        PageBean pageBean=favoriteService.showpagefavorite(uid,currentPage ,pageSize );
        witevalue(pageBean,response );
    }
}