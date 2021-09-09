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
public class PojoComment implements Serializable {
    private int id;
    private int tableId;
    //评论者id
    private int user_id;

    //是否为匿名
    private boolean anonymous;

    //匿名名称
    private String name;

    private Date createTime;
    private String content;

    //图片列表
    private String images;

    //对应的用户公开信息
    private PojoUserPublic userPublic;

    //状态 0为正常 1为待审核 -1为封禁
    private int status;
}
