package com.fansz.members.api.utils;


import java.util.Arrays;
import java.util.List;

/**
 * File 辅助类
 * 
 * Created by viking on 15/8/12.
 */
public class FileOperator {
    /**
     * Java文件操作 获取文件扩展名
     *
     * @param filename 文件名
     * @return 文件名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     * @param filename 文件名
     * @return 文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 判断文件类型（后缀）是否合法
     *
     * @param filename 文件名
     * @return 是否合法
     */
    public static Boolean isAllowedExtension(String filename) {
        Boolean returnFlag = false;
        String fileExtension = getExtensionName(filename);
        List<String> allowedExtenList = Arrays.asList("JPG", "PNG", "GIF");
        for (String ext : allowedExtenList) {
            if (fileExtension.equalsIgnoreCase(ext)) {
                returnFlag = true;
            }
        }
        return returnFlag;
    }
}
