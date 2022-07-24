package cn.conststar.wall.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Base64;

public class HashUtils {

    public static final String HASH_ALGORITHM_NAME = "SHA-256";
    public static final int HASH_ITERATIONS = 1024;

    public static String simpleHash(String password, String salt) {
        return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
    }

    public static String base64(String src) {
        return Base64.getEncoder().encodeToString(src.getBytes());
    }

    public static String deBase64(String src) {
        return new String(Base64.getDecoder().decode(src));
    }
}
