package com.moon.core.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type MD5 utils.
 */
public class MD5Utils {

    public static String md5(String plainText) {
        return md5(null, plainText);
    }

    public static String md5(String salt, String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (salt != null) {
                md.update(salt.getBytes());
            }
            md.update(plainText.getBytes());
            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
