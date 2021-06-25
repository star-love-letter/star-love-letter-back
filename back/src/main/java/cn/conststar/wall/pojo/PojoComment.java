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

    private String name;
    private Date createTime;
    private String content;
}
