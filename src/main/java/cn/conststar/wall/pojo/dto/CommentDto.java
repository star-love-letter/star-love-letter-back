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
@ApiModel("评论信息")
public class CommentDto implements Serializable {

    @ApiModelProperty("评论id")
    private int id;

    @ApiModelProperty("帖子id")
    private int tableId;

    @ApiModelProperty("评论者id")
    private int userId;

    @ApiModelProperty("评论创建时间")
    private Date createTime;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("图片列表 需要手动转为json")
    private String images;

    @ApiModelProperty("对应的用户公开信息")
    private UserInfoPublicDto userPublic;

    @ApiModelProperty(value = "状态", notes = "0:正常 1:待审核 -1:锁定 -2:禁言 详细请看src/main/java/cn/conststar/wall/constant/StateCodeConstant.java")
    private int state;
}
