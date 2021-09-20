package cn.conststar.wall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ConfigJackson extends ObjectMapper {
    public ConfigJackson() {
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
