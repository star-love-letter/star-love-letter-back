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
@ApiModel("评论实体")
public class PojoComment implements Serializable {

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

    @ApiModelProperty("图片列表")
    private String images;

    @ApiModelProperty("对应的用户公开信息")
    private PojoUserPublic userPublic;

    @ApiModelProperty("状态 0为正常 1为待审核 -1为封禁")
    private int status;
}
