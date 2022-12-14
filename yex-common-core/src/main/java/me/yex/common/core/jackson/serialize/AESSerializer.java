package me.yex.common.core.jackson.serialize;

import me.yex.common.core.util.AESUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.core.jackson.serialize
 */
public class AESSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value != null) {
            gen.writeString(AESUtil.getCipher().decryptStr(value));
        }
    }
}
