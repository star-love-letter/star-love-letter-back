package cn.conststar.wall.pojo.dto;

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
@ApiModel("帖子信息")
public class TableDto implements Serializable {

    @ApiModelProperty("帖子id")
    private int id;

    @ApiModelProperty("是否为匿名")
    private boolean anonymous;

    @ApiModelProperty("是否点过赞")
    private boolean support;

    @ApiModelProperty("表白者姓名")
    private String sender;

    @ApiModelProperty(value = "表白者性别", notes = "1男生 2女生 0保密")
    private int senderGender;

    @ApiModelProperty("被表白者姓名")
    private String recipient;

    @ApiModelProperty(value = "被表白者性别", notes = "1男生 2女生 0保密")
    private int recipientGender;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("表白内容")
    private String content;

    @ApiModelProperty("点赞的数量")
    private int supportCount;

    @ApiModelProperty("评论的数量")
    private int commentCount;

    @ApiModelProperty("图片列表 需要手动转为json")
    private String images;

    @ApiModelProperty("对应的用户公开信息")
    private UserInfoPublicDto userPublic;

    @ApiModelProperty("是否邮箱通知")
    private boolean notifyEmail;

    @ApiModelProperty(value = "状态", notes = "0:正常 1:待审核 -1:锁定 -2:禁言 详细请看src/main/java/cn/conststar/wall/constant/StateCodeConstant.java")
    private int state;
}
