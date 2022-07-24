package cn.conststar.wall.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("统一响应实体")
public class GenericHandler<T> implements Serializable {

    @ApiModelProperty(value = "状态码", required = true)
    private int code;

    @ApiModelProperty(value = "消息", required = true)
    private String message;

    @ApiModelProperty(value = "数据", required = false, allowEmptyValue = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 对业务数据单独处理
     *
     * @return
     */
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}