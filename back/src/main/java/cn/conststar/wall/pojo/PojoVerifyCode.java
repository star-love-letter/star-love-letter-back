package cn.conststar.wall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PojoVerifyCode {

    //图片验证码
    String imageCode = "";
    //图片验证码获取时间
    Long imageCodeTime = 0L;
    //图片验证码验证错误次数
    Integer imageCodeVerifyAns = 0;

    //邮箱验证码
    String emailCode = "";
    //验证的邮箱
    String emailVerify = "";
    //邮箱验证码获取时间
    Long emailCodeTime = 0L;
    //邮箱验证码验证错误次数
    Integer emailCodeVerifyAns = 0;

    //邮箱验证码获取总次数
    Integer emailCodeAns = 0;

}
