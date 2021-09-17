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
@ApiModel("帖子实体")
public class PojoTable implements Serializable {

    @ApiModelProperty("帖子id")
    private int id;

    @ApiModelProperty("是否为匿名")
    private boolean anonymous;

    @ApiModelProperty("是否点过赞")
    private boolean support;

    @ApiModelProperty("表白者姓名")
    private String sender;

    @ApiModelProperty("表白者性别")
    private int senderSex;

    @ApiModelProperty("被表白者姓名")
    private String recipient;

    @ApiModelProperty("被表白者性别")
    private int recipientSex;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("表白内容")
    private String content;

    @ApiModelProperty("点赞数量")
    private int supportCount;

    @ApiModelProperty("评论数量")
    private int commentCount;

    @ApiModelProperty("图片列表")
    private String images;

    @ApiModelProperty("对应的用户公开信息")
    private PojoUserPublic userPublic;

    @ApiModelProperty("状态 0为正常 1为待审核 -1为封禁")
    private int status;
}
