package me.yex.common.core.util;


import cn.hutool.crypto.symmetric.AES;

/**
 * @author yex
 * @description cn.zhenhealth.health.user.util
 */
public class AESUtil {

    private static final String KEY = "ZHENhealthZZZHEN";

    public static AES getCipher(){
        return new AES("ECB", "PKCS7Padding", KEY.getBytes());
    }

    public static AES getCipher(String mode, String padding){
        return new AES(mode, padding, KEY.getBytes());
    }


    public static void main(String[] args) {

        String content = "111";

        String encryptHex = AESUtil.getCipher().encryptHex(content);
        System.out.println(encryptHex);
        // 解密
        String decryptStr = AESUtil.getCipher().decryptStr(encryptHex);
        System.out.println(decryptStr);

    }

}
