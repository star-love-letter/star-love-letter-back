package cn.conststar.wall.pojo;

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
@ApiModel("用户公开信息")
public class PojoUserPublic implements Serializable {

    @ApiModelProperty("账号id")
    private int id;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("学号")
    private String studentId;

    @ApiModelProperty("注册时间")
    private Date createTime;

    @ApiModelProperty("最近登录时间")
    private Date lastTime;
}
