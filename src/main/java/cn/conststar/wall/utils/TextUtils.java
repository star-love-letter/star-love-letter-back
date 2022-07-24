package cn.conststar.wall.utils;

import java.util.regex.Pattern;

public class TextUtils {
    //检查文本是否需要审核
    public static boolean checkText(String text) {

        //域名
        boolean m1 = Pattern.matches("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?", text);
        if (m1)
            return true;

        //邮箱
        boolean m2 = Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", text);
        if (m2)
            return true;

        //手机号
        boolean m3 = Pattern.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", text);
        if (m3)
            return true;

        //座机
        boolean m4 = Pattern.matches("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)", text);
        if (m4)
            return true;

        //身份证
        boolean m5 = Pattern.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", text);
        if (m5)
            return true;

        //QQ
        boolean m6 = Pattern.matches("[1-9][0-9]{4,}", text);
        if (m6)
            return true;

        //ipv4
        boolean m7 = Pattern.matches("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}", text);
        if (m7)
            return true;

        return false;
    }
    
}
