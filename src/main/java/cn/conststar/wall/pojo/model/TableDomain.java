package cn.conststar.wall.pojo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("帖子实体")
public class TableDomain {

    @ApiModelProperty("帖子id")
    private int id;

    @ApiModelProperty("是否为匿名")
    private boolean anonymous;

    @Size(min = 1, max = 6, message = "表白者名称必须在1到6个字符以内")
    @ApiModelProperty("表白者姓名")
    private String sender;

    @ApiModelProperty(value = "表白者性别",notes = "1男生 2女生 0保密")
    private int senderGender;

    @Size(min = 1, max = 6, message = "被表白者名称必须在1到6个字符以内")
    @ApiModelProperty("被表白者姓名")
    private String recipient;

    @ApiModelProperty(value = "被表白者性别",notes = "1男生 2女生 0保密")
    private int recipientGender;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @Size(min = 1, max = 300, message = "表白内容必须在1到300个字以内")
    @ApiModelProperty("表白内容")
    private String content;

    @ApiModelProperty("图片列表 需要手动转为json")
    private String images;

    @ApiModelProperty("对应的用户id")
    private int userId;

    @ApiModelProperty("是否邮箱通知")
    private boolean notifyEmail;

    @ApiModelProperty(value = "状态", notes = "0:正常 1:待审核 -1:锁定 -2:禁言 详细请看src/main/java/cn/conststar/wall/constant/StateCodeConstant.java")
    private int state;

    @ApiModelProperty("状态名称")
    private String stateName;
}
