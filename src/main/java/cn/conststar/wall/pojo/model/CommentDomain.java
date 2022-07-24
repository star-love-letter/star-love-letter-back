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
@ApiModel("评论实体")
public class CommentDomain {
    @ApiModelProperty("评论id")
    private int id;

    @ApiModelProperty("帖子id")
    private int tableId;

    @ApiModelProperty("评论者id")
    private int userId;

    @ApiModelProperty("评论创建时间")
    private Date createTime;

    @Size(min = 1, max = 150, message = "评论内容必须在1到150个字以内")
    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("图片列表 需要手动转为json")
    private String images;

    @ApiModelProperty(value = "状态", notes = "0:正常 1:待审核 -1:锁定 -2:禁言 详细请看src/main/java/cn/conststar/wall/constant/StateCodeConstant.java")
    private int state;
}
