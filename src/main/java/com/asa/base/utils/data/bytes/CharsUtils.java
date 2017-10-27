package com.asa.base.utils.data.bytes;

/**
 * Created by andrew_asa on 2017/7/21.
 */
public class CharsUtils {

    public static void paddingChar(char[] chars, char padding) {

        if (chars != null && chars.length > 0) {
            for (int i = 0; i < chars.length; i++) {
                chars[i] = padding;
            }
        }
    }

    public static void paddingCharArray(char[] chars, char[] paddings) {

        if (chars != null && chars.length > 0 && paddings != null && paddings.length > 0) {
            int paddingSize = Math.min(chars.length, paddings.length);
            for (int i = 0; i < paddingSize; i++) {
                chars[i] = paddings[i];
            }
        }
    }
}
