package cn.conststar.wall.pojo;

import cn.conststar.wall.exception.ExceptionMain;
import cn.conststar.wall.response.ResponseCodeEnums;
import cn.conststar.wall.utils.UtilsMain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PojoToken implements Serializable {
    public class TYPE {
        public static final int Email = 0;
        public static final int WeChat = 1;
    }

    public PojoToken(int userId, int type, String data) {
        this.userId = userId;
        this.type = type;
        this.UUID = UtilsMain.getUUID();
        this.createDate = System.currentTimeMillis();
        this.data = data;
    }

    //过期时间
    @JsonIgnore
    private static final long EXPIRE = 1000 * 60 * 60 * 24;

    String UUID;
    int userId;
    int type;
    long createDate;
    String data;

    @JsonIgnore
    public boolean isExpire() {
        return this.type != PojoToken.TYPE.WeChat && System.currentTimeMillis() - createDate > EXPIRE;
    }

    public String toToken() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(this);
        return UtilsMain.base64(json);
    }

    public static PojoToken getToken(String token) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = UtilsMain.deBase64(token);
            return mapper.readValue(json, PojoToken.class);
        }catch (Exception exception){
            throw new ExceptionMain("token失效", ResponseCodeEnums.CODE_20001);
        }
    }
}
