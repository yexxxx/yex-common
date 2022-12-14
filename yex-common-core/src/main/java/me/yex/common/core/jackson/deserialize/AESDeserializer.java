package me.yex.common.core.jackson.deserialize;

import me.yex.common.core.util.AESUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.core.jackson.deserialize
 */
public class AESDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return ObjectUtils.isEmpty(p.getText()) ? p.getText() : AESUtil.getCipher().encryptHex(p.getText());
    }
}
