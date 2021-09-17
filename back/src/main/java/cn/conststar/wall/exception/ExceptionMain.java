package cn.conststar.wall.exception;

import cn.conststar.wall.response.ResponseCodeEnums;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

@Getter
@Setter
public class ExceptionMain extends RuntimeException {

    private Logger logger = Logger.getLogger(ExceptionMain.class);
    private Exception exception;
    private ResponseCodeEnums status;

    public ExceptionMain(String message) {
        super(message);
        this.exception = new Exception(message);
        this.status = ResponseCodeEnums.CODE_201;
    }

    public ExceptionMain(String message, ResponseCodeEnums status) {
        super(message);

        this.exception = new Exception(message);
        this.status = status;
    }
}
