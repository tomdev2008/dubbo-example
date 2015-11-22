package com.fansz.members.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityTools {

    private final static Logger logger = LoggerFactory.getLogger(SecurityTools.class);

    public static String encode(String inStr) {
        String encodedStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            encodedStr = hexValue.toString();
        } catch (Exception e) {
            logger.error("errors happened when encoded using MD5", e);
        }

        return encodedStr;

    }
}
