package cn.conststar.wall.exception;

import cn.conststar.wall.response.ResponseCodeEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionMain extends RuntimeException {
    private Exception exception;
    private ResponseCodeEnums code;

    public ExceptionMain(String message) {
        super(message);

        this.exception = new Exception(message);
        this.code = ResponseCodeEnums.CODE_201;
    }

    public ExceptionMain(String message, ResponseCodeEnums code) {
        super(message);

        this.exception = new Exception(message);
        this.code = code;
    }
}
