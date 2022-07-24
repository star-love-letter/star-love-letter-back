package cn.conststar.wall.handler;

import cn.conststar.wall.exception.BusinessException;
import cn.conststar.wall.constant.ResponseEnumConstant;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;


@ApiOperation("全局异常处理")
@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = Logger.getLogger(this.getClass());

    @ApiOperation("捕获全部异常")
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public GenericHandler<Object> errorHandler(Exception ex) {
        ex.printStackTrace();
        logger.error(ex.toString());
        return FormatHandler.retParam(ResponseEnumConstant.CODE_1000, null, ex.getMessage());
    }

    @ApiOperation("捕获自定义异常")
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public GenericHandler<Object> myErrorHandler(BusinessException ex) {
        Exception exception = ex.getException();
        exception.printStackTrace();

        ResponseEnumConstant code = ex.getCode();
        int codeValue = code.getCode();

        if (200 <= codeValue && codeValue <= 299) {
            logger.info(exception.toString());
        } else if (20001 <= codeValue && codeValue <= 29999) {
            logger.warn(exception.toString());
        } else {
            logger.error(exception.toString());
        }

        return FormatHandler.retParam(code, null, exception.getMessage());
    }

    @ApiOperation("捕获异常-用户未登录")
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public GenericHandler<Object> unauthenticatedExceptionHandler(UnauthenticatedException ex) {
        return FormatHandler.retParam(ResponseEnumConstant.CODE_20001, null);
    }

    @ApiOperation("捕获异常-权限不足")
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public GenericHandler<Object> unauthorizedExceptionHandler(UnauthorizedException ex) {
        return FormatHandler.retParam(ResponseEnumConstant.CODE_70001, null);
    }

    @ApiOperation("捕获异常-没有此文件")
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseBody
    public void fileNotFoundExceptionHandler(FileNotFoundException ex,
                                             HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}