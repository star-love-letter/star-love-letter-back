package cn.conststar.wall.pojo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("点赞实体")
public class SupportDomain {
    @ApiModelProperty("用户id")
    private int userId;

    @ApiModelProperty("帖子id")
    private int tableId;
}
