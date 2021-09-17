package cn.conststar.wall.response;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统一响应实体")
public class ResponseGeneric<T> implements Serializable{

    @ApiModelProperty(value = "状态码",required=true)
    private int code;

    @ApiModelProperty(value = "消息",required = true)
    private String message;

    @ApiModelProperty(value = "数据",required = false,allowEmptyValue = true)
    private T data;

    /**
     * 对业务数据单独处理
     * @return
     */
    @Override
    public String toString() {
        if(Objects.isNull(this.data)){
            this.setData((T) new Object());
        }
        return JSON.toJSONString(this);
    }
}