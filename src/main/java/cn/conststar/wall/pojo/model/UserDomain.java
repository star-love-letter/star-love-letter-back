package cn.conststar.wall.pojo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel("用户实体")
public class UserDomain {
    @ApiModelProperty("账号id")
    private int id;

    @ApiModelProperty("用户密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty("盐")
    @JsonIgnore
    private String salt;

    @Email(message = "邮箱格式错误")
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("微信")
    private String wechat;

    @ApiModelProperty("头像")
    private String avatar;

    @Size(min = 1, max = 6, message = "名称必须在1到6个字符以内")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("学号")
    private int studentId;

    @ApiModelProperty("注册时间")
    private Date createTime;

    @ApiModelProperty("最近登录时间")
    private Date lastTime;

    @ApiModelProperty(value = "状态", notes = "0:正常 1:待审核 -1:锁定 -2:禁言 详细请看src/main/java/cn/conststar/wall/constant/StateCodeConstant.java")
    private int state;

    @ApiModelProperty("角色id")
    private int roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @Override
    public String toString() {
        return "UserDomain{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", wechat='" + wechat + '\'' +
                ", name='" + name + '\'' +
                ", studentId=" + studentId +
                ", createTime=" + createTime +
                ", lastTime=" + lastTime +
                ", state=" + state +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
