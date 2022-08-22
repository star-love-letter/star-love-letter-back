package cn.conststar.wall.interceptor;

import cn.conststar.wall.config.GlobalConfig;
import cn.conststar.wall.constant.ResponseEnumConstant;
import cn.conststar.wall.exception.BusinessException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域支持
        response.setHeader("Access-Control-Allow-Origin", GlobalConfig.corsOrigin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");

        // 不受限制的路径
        String servletPath = request.getServletPath();
        if (servletPath != null && (servletPath.equals("/api/view/getView")
                || servletPath.startsWith("/api/admin")
                || servletPath.startsWith("/api/user/login")
                || servletPath.startsWith("/api/user/user")
                || servletPath.startsWith("/api/file/image"))) {
            return true;
        }

        // 网站是否关闭
        if (GlobalConfig.viewStop)
            throw new BusinessException(GlobalConfig.viewStopReason, ResponseEnumConstant.CODE_60003);

        return true;
    }
}
