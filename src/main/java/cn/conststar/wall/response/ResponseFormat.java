package cn.conststar.wall.response;

public class ResponseFormat {

    public static <T> ResponseGeneric<T> retParam(ResponseCodeEnums status, T data) {
        ResponseGeneric<T> json = new ResponseGeneric<T>(status.code, status.getDesc(), data);
        return json;
    }

    public static <T> ResponseGeneric<T> retParam(ResponseCodeEnums status, T data,String message) {
        ResponseGeneric<T> json = new ResponseGeneric<T>(status.code, message, data);
        return json;
    }
}