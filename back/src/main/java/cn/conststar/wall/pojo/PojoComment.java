package cn.conststar.wall.pojo;

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
    private int userId;

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
}
