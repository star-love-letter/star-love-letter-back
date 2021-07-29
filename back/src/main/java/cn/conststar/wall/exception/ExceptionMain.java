package cn.conststar.wall.exception;

import org.apache.log4j.Logger;

public class ExceptionMain extends Exception {

    //默认错误
    public static final int DEFAULT = 100;

    //没有登录账号
    public static final int NOT_LOGIN = 101;

    //致命错误
    public static final int DEADLY = 200;


    private Logger logger = Logger.getLogger(ExceptionMain.class);
    private int code = DEFAULT;


    public ExceptionMain() {
        super();
    }

    public ExceptionMain(String message) {
        super(message);
    }

    public ExceptionMain(String message, int code) {
        super(message);
        this.code = code;

        if (code == DEADLY) {
            logger.error(message);
        }
    }

    public int getCode() {
        return code;
    }
}
