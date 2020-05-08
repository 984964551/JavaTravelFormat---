package cn.test.web.servlet;

import cn.test.domain.ResultInfo;
import cn.test.domain.User;
import cn.test.service.UserService;
import cn.test.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //激活状态的activeUserServlet
    public static void activeUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        //激活验证码
        UserService userService=new UserServiceImpl();
        boolean flag=userService.active(code);
        String msg=null;
        if (flag){
            //激活成功
            msg="激活成功,请点击<a href='http://localhost/login.html'>登录";
        }else {
            msg="登录失败,请联系管理员";
        }
        //设置编码格式
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

   //用户退出的exitServlet
   public static void exitServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //销毁session
       request.getSession().invalidate();
       //重定向至主页
       response.sendRedirect(request.getContextPath()+"/index.html");
   }

   //用户登录的loginUserServlet
   public static void loginUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //获取用户登录的数据
       String username = request.getParameter("username");
       String password = request.getParameter("password");
       String checkcode=request.getParameter("check");
       //创建空白用户来接收数据
       User user=new User();
       user.setUsername(username);
       user.setPassword(password);
       //创建session获取验证码
       HttpSession session=request.getSession();
       //获取正确的验证码
       String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
       //移出生成的验证码避免再次返回时验证码可用
       session.removeAttribute("CHECKCODE_SERVER");
       //创建响应消息对象
       ResultInfo resultInfo=new ResultInfo();
       //创建序列化对象json将数据响应给前台
       ObjectMapper objectMapper=new ObjectMapper();
       //判断验证码
       if (checkcode_server.equals(checkcode)){
           //验证码正确,调用UserService方法判断用户是否存在
           UserService userService=new UserServiceImpl();
           User user1=userService.find(user);
           if (user1!=null){
               //用户存在，调用UserService方法判断用户激活码是否激活
               boolean status=userService.status(user);
               if (status){
                   //用户登录成功后设置一个session显示在前台页面
                   session.setAttribute("user",user1 );
                   //激活码已激活
                   resultInfo.setFlag(true);
                   String json = objectMapper.writeValueAsString(resultInfo);
                   //设置编码格式
                   response.setContentType("application/json;charset=utf-8");
                   response.getWriter().write(json);
               }else {
                   //激活码未激活
                   resultInfo.setFlag(false);
                   resultInfo.setErrorMsg("检测到您的邮箱尚未验证，请先验证");
                   String json = objectMapper.writeValueAsString(resultInfo);
                   //设置编码格式
                   response.setContentType("application/json;charset=utf-8");
                   response.getWriter().write(json);
               }
           }else {
               //用户不存在
               //验证码错误返回false
               resultInfo.setFlag(false);
               resultInfo.setErrorMsg("用户名或密码错误");
               String json = objectMapper.writeValueAsString(resultInfo);
               //设置编码格式
               response.setContentType("application/json;charset=utf-8");
               response.getWriter().write(json);
           }
       }
       else {
           //验证码错误返回false
           resultInfo.setFlag(false);
           resultInfo.setErrorMsg("验证码错误");
           String json = objectMapper.writeValueAsString(resultInfo);
           //设置编码格式
           response.setContentType("application/json;charset=utf-8");
           response.getWriter().write(json);
       }
   }

   //用户注册的registUserServlet
   public static void registUserServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //检验验证码
       //获取用户的验证码
       String checkcode=request.getParameter("check");
       //从session中获取正确的验证码
       HttpSession httpSession=request.getSession();
       String checkcode_server = (String) httpSession.getAttribute("CHECKCODE_SERVER");
       httpSession.removeAttribute("CHECKCODE_SERVERr");
       //二者进行比较
       if (!checkcode_server.equals(checkcode)){
           //两者不相等
           ResultInfo resultInfo=new ResultInfo();
           resultInfo.setFlag(false);
           resultInfo.setErrorMsg("验证码错误");
           ObjectMapper objectMapper=new ObjectMapper();
           String jsion = objectMapper.writeValueAsString(resultInfo);
           response.setContentType("application/json;charset=utf-8");
           response.getWriter().write(jsion);
           return;
       }
       //获取用户数据
       String username = request.getParameter("username");
       String password = request.getParameter("password");
       String email = request.getParameter("email");
       String name = request.getParameter("name");
       String telephone = request.getParameter("telephone");
       String sex = request.getParameter("sex");
       String birthday = request.getParameter("birthday");
       //创建一个接收数据的用户对象
       User user=new User();
       user.setUsername(username);
       user.setPassword(password);
       user.setEmail(email);
       user.setName(name);
       user.setTelephone(telephone);
       user.setSex(sex);
       user.setBirthday(birthday);
//        Map<String, String[]> map = request.getParameterMap();
//        User user=new User();
//        try {
//            BeanUtils.populate(user,map );
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
       //调用UserService方法完成注册
       UserService userService=new UserServiceImpl();
       boolean flag=userService.regist(user);
       ResultInfo resultInfo=new ResultInfo();
       //响应数据
       if (flag==true){
           resultInfo.setFlag(true);
       }else {
           resultInfo.setFlag(false);
           resultInfo.setErrorMsg("用户名已存在");
       }
       //将info对象序列化位json
       ObjectMapper objectMapper = new ObjectMapper();
       String json = objectMapper.writeValueAsString(resultInfo);
       //将json写回客户端
       //设置content-type
       response.setContentType("application/json;charset=utf-8");
       //字符流输出
       response.getWriter().write(json);
       //字节流输出
//        response.getOutputStream().wait(json.getBytes());
   }

   //显示用户登录用户名的showusernameServlet
   public static void showusernameServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       HttpSession session = request.getSession();
       Object user = session.getAttribute("user");
       ObjectMapper objectMapper=new ObjectMapper();
       String json = objectMapper.writeValueAsString(user);
       response.setContentType("application/json;charset=utf-8");
       response.getWriter().write(json);
   }
}
