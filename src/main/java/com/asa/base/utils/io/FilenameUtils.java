package com.asa.base.utils.io;

import com.asa.base.utils.data.string.StringUtils;

/**
 * @author andrew.asa
 * @create 2017-11-12
 **/
public class FilenameUtils {

    public static String getSuffixName(String fileName) {

        if (StringUtils.isNotEmpty(fileName)) {
            int index = fileName.lastIndexOf(".");
            if (index > 0) {
                return fileName.substring(index + 1);
            } else {
                return fileName;
            }
        }
        return "";
    }
}
