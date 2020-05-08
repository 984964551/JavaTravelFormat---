package cn.test.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    //通过方法名调用Servlet
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI();
        //获取方法名称
        String methodname = uri.substring(uri.lastIndexOf('/') + 1);
        try {
            //获取方法对象method
            //谁调用，this代表谁
            Method method = this.getClass().getMethod(methodname, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //序列化json,写回客户端
    public void witevalue(Object object,HttpServletResponse response ) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),object);
    }
    //序列化json，返回
    public void writeValueAsString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValueAsString(object);
    }
}
