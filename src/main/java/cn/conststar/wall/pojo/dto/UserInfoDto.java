package cn.conststar.wall.pojo.dto;

import cn.conststar.wall.pojo.model.UserDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户资料")
public class UserInfoDto implements Serializable {

    @ApiModelProperty("账号id")
    private int id;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("头像")
    private String avatar;

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

    public UserInfoDto(UserDomain userDomain) {
        this.id = userDomain.getId();
        this.email = userDomain.getEmail();
        this.name = userDomain.getName();
        this.studentId = userDomain.getStudentId();
        this.createTime = userDomain.getCreateTime();
        this.lastTime = userDomain.getLastTime();
        this.state = userDomain.getState();
        this.roleId = userDomain.getRoleId();
        this.roleName = userDomain.getRoleName();
        this.avatar = userDomain.getAvatar();
    }
}
