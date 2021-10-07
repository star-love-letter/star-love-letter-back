package cn.conststar.wall.pojo;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.utils.UtilsEmail;
import cn.conststar.wall.utils.UtilsVerifyCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @ApiModelProperty("是否邮箱通知")
    private boolean notifyEmail;

    @ApiModelProperty("状态 0为正常 1为待审核 -1为封禁")
    private int status;

    //获取并发送邮箱验证码
    public void sendNotifyEmail(String content, List<String> images, int status) throws Exception {
        if (!this.notifyEmail)
            return;

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("刚刚有人评论了你的表白\n");
        messageBuilder.append("\n评论内容: " + content);
        if (images != null) {
            for (String image : images) {
                messageBuilder.append("[图片]");
            }
        }

        if (status == 1)
            messageBuilder.append("\n帖子状态: 待审核");
        else if (status == -1)
            messageBuilder.append("\n帖子状态: 已封禁");

        //待优化
        messageBuilder.append("\n帖子地址: https://wall.conststar.cn/#/TableDetail/" + this.id);

        UtilsEmail.sendAsync("刚刚有人评论了你的表白", messageBuilder.toString(), this.userPublic.getEmail(), "星愿表白墙");
    }
}
