package cn.conststar.wall.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("页面显示")
public class ViewDto {
    @ApiModelProperty("页面头部")
    private String header;

    @ApiModelProperty("页面底部")
    private String footer;

    @ApiModelProperty("关闭网站")
    private boolean stop;

    @ApiModelProperty("关闭网站的原因")
    private String stopReason;

    @ApiModelProperty("统计代码")
    private String statsCode;
}
