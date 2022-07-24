package cn.conststar.wall.pojo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("后台--系统设置实体")
public class SettingDomain {

    @ApiModelProperty("设置名称")
    private String name;

    @ApiModelProperty("设置值")
    private String value;

    @ApiModelProperty("设置描述")
    private String description;
}
