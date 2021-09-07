package cn.conststar.wall.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PojoTable implements Serializable {
    private int id;

    //是否为匿名
    private boolean anonymous;
    //是否点过赞
    private boolean support;

    private String sender;
    private int senderSex;

    private String recipient;
    private int recipientSex;

    private Date createTime;
    private String content;

    //点赞数量
    private int supportCount;

    //评论数量
    private int commentCount;

    //图片列表
    private String images;

    //对应的用户公开信息
    private PojoUserPublic userPublic;
}
