package cn.conststar.wall.pojo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("日志实体")
public class LogDomain {

    @ApiModelProperty("日志id")
    private int id;

    @ApiModelProperty("产生的动作")
    private String action;

    @ApiModelProperty("产生的数据")
    private String data;

    @ApiModelProperty("用户id")
    private int userId;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
