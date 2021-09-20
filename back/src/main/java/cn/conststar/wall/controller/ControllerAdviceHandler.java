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

    @ApiOperation("捕获全是异常")
    @ExceptionHandler(value = Exception.class)
    public ResponseGeneric errorHandler(Exception ex) {
        ex.printStackTrace();
        logger.error(ex.toString());
        return ResponseFormat.retParam(ResponseCodeEnums.CODE_1000, null, ex.getMessage());
    }

    @ApiOperation("捕获自定义异常")
    @ExceptionHandler(value = ExceptionMain.class)
    public ResponseGeneric myErrorHandler(ExceptionMain ex) {
        Exception exception = ex.getException();
        exception.printStackTrace();
        logger.warn(exception.toString());
        return ResponseFormat.retParam(ex.getCode(), null, exception.getMessage());
    }
}