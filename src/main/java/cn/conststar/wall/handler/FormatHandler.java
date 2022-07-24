package cn.conststar.wall.handler;

import cn.conststar.wall.constant.ResponseEnumConstant;

public class FormatHandler {

    public static <T> GenericHandler<T> retParam(ResponseEnumConstant status, T data) {
        GenericHandler<T> json = new GenericHandler<T>(status.getCode(), status.getDesc(), data);
        return json;
    }

    public static <T> GenericHandler<T> retParam(ResponseEnumConstant status, T data, String message) {
        GenericHandler<T> json = new GenericHandler<T>(status.getCode(), message, data);
        return json;
    }
}