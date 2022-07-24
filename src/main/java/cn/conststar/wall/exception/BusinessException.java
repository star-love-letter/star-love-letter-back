package cn.conststar.wall.exception;

import cn.conststar.wall.constant.ResponseEnumConstant;
import lombok.Getter;
import lombok.Setter;

/*
    统一异常类
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {
    private Exception exception;
    private ResponseEnumConstant code;

    //默认201错误（操作失败）
    public BusinessException(String message) {
        super(message);

        this.exception = new Exception(message);
        this.code = ResponseEnumConstant.CODE_201;
    }

    //自定义异常信息
    public BusinessException(String message, ResponseEnumConstant code) {
        super(message);

        this.exception = new Exception(message);
        this.code = code;
    }

    //使用默认异常信息
    public BusinessException(ResponseEnumConstant code) {
        super(code.getDesc());

        this.exception = new Exception(code.getDesc());
        this.code = code;
    }
}
