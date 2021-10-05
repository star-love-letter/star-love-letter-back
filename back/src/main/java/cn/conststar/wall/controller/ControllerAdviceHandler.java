package cn.conststar.wall.controller;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.response.ResponseFormat;
import cn.conststar.wall.response.ResponseGeneric;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;


@ApiOperation("Controller增强器")
@RestControllerAdvice
public class ControllerAdviceHandler {

    Logger logger = Logger.getLogger(this.getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }


    @ModelAttribute
    public void addAttributes(Model model) {

    }

    @ApiOperation("捕获全部异常")
    @ExceptionHandler(Exception.class)
    public ResponseGeneric<Object> errorHandler(Exception ex) {
        ex.printStackTrace();
        logger.error(ex.toString());
        return ResponseFormat.retParam(ResponseCodeEnums.CODE_1000, null, ex.getMessage());
    }

    @ApiOperation("捕获自定义异常")
    @ExceptionHandler(ExceptionMain.class)
    public ResponseGeneric<Object> myErrorHandler(ExceptionMain ex) {
        Exception exception = ex.getException();
        exception.printStackTrace();

        ResponseCodeEnums code = ex.getCode();
        int codeValue = code.getCode();

        if (200 <= codeValue && codeValue <= 299) {
            logger.info(exception.toString());
        } else if (20001 <= codeValue && codeValue <= 29999) {
            logger.warn(exception.toString());
        } else {
            logger.error(exception.toString());
        }

        return ResponseFormat.retParam(code, null, exception.getMessage());
    }

    @ApiOperation("捕获异常-没有此文件")
    @ExceptionHandler(FileNotFoundException.class)
    public void errorHandler(FileNotFoundException ex,
                             HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}