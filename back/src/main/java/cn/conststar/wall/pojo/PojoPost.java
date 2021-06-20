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
public class PojoPost implements Serializable {
    private int id;

    private String sender;
    private int senderSex;

    private String recipient;
    private int recipientSex;

    private Date createTime;
    private String content;

    private int thumbsUp;       //点赞数量
    private int commentCount;   //评论数量
}
