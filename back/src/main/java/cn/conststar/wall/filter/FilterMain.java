package cn.conststar.wall.filter;

import cn.conststar.wall.exception.ExceptionMain;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import java.io.IOException;

public class FilterMain implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            chain.doFilter(request, response);
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

            response.getWriter().write(jsonObject.toJSONString());
        }
    }

    @Override
    public void destroy() {

    }
}
