package cn.conststar.wall.filter;

import cn.conststar.wall.exception.ExceptionMain;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Locale;

public class FilterMain implements Filter {
    Logger logger = Logger.getLogger(FilterMain.class);


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

        /***
         * 开发环境
         */

        //日志
        String url = req.getRequestURI();
        String method = req.getMethod();
        String origin = req.getHeader("Origin");
        String token = req.getHeader("token");
        String parameter = JSONObject.toJSONString(req.getParameterMap());

        String requestInfo = "\n"
                + method + ":" + url
                + "\norigin:" + origin
                + "\nparameter:" + parameter
                + "\ntoken:" + token
                + "\n";

        if (url.startsWith("/api"))
            logger.info(requestInfo);

        //跨域
        res.setHeader("Pragma", "No-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Accept, Origin, User-Agent, Content-Range, Content-Disposition, Content-Description, token");
        res.addHeader("Access-Control-Allow-Credentials", "true"); // 允许携带验证信息
        res.setDateHeader("Expires", -10);
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {

    }
}
