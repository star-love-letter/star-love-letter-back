package cn.conststar.wall.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体")
public class PojoUser implements Serializable {

    @ApiModelProperty("账号id")
    private int id;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("学号")
    private String student_id;

    @ApiModelProperty("注册时间")
    private Date createTime;

    @ApiModelProperty("最近登录时间")
    private Date lastTime;

    @ApiModelProperty("状态 0为正常 1为待审核 -1为封禁")
    private int status;
}
