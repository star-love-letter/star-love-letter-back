package cn.conststar.wall.filter;

import cn.conststar.wall.exception.ExceptionMain;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterMain implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        try {
            //开发环境
            res.setHeader("Pragma", "No-cache");
            res.setHeader("Cache-Control", "no-cache");
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD, DELETE, PUT");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Accept, Origin, User-Agent, Content-Range, Content-Disposition, Content-Description");
            res.setDateHeader("Expires", -10);
            chain.doFilter(request, res);


//            chain.doFilter(request, response);
        } catch (Exception ex) {
            JSONObject jsonObject = new JSONObject();
            Throwable cause = ex.getCause();

            if (cause instanceof ExceptionMain) {
                ExceptionMain exceptionMain = (ExceptionMain) cause;
                jsonObject.put("msg", exceptionMain.getMessage());
                jsonObject.put("Exception", exceptionMain.toString());
                jsonObject.put("code", exceptionMain.getCode());
            } else if (cause instanceof Exception) {
                Exception exception = (Exception) cause;
                jsonObject.put("msg", exception.getMessage());
                jsonObject.put("Exception", exception.toString());
                jsonObject.put("code", -1);
            } else {
                jsonObject.put("msg", ex.getMessage());
                jsonObject.put("Exception", ex.toString());
                jsonObject.put("code", -2);
            }

            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(jsonObject.toJSONString());
        }
    }

    @Override
    public void destroy() {

    }
}
