package com.asa.base.utils.data.string;


/**
 * Created by andrew_asa on 2017/9/29.
 */
public class StringUtils {

    public static String EMPTY = "";

    /**
     * 以前的所谓StringUtils都是用私有的构造方法但是这是不合理的，因为如果本utils方法里面不实现
     * 别人想继承怎么办
     */
    public StringUtils() {

    }

    public static String setIndex(String str, int index, String c) {

        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, index) + c + str.substring(index);
    }

    /**
     * 字符串为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {

        return s == null || EMPTY.equals(s);
    }

    /**
     * 非空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {

        return !isEmpty(s);
    }

    /**
     * 去掉字符串头部的斜线
     *
     * @param location 目标字符串
     * @return 没有头部斜线的字符串。
     */
    public static String cutStartSlash(String location) {

        return cutStartChar(location, "/");
    }

    /**
     * 递归去除目标字符串尾部字符。
     * "////a/b/",去除头部"/"的话，结果为"a/b/"
     *
     * @param target  目标字符串；
     * @param cutChar 需要切除的头部
     * @return 处理后的字符
     */
    public static String cutStartChar(String target, String cutChar) {

        if (target != null) {
            if (target.startsWith(cutChar)) {
                return cutStartChar(target.substring(cutChar.length()), cutChar);
            } else {
                return target;
            }
        } else {
            return target;
        }
    }

    /**
     * 去掉字符串尾部的斜线
     *
     * @param location 目标字符串
     * @return 没有尾部斜线的字符串。
     */
    public static String cutEndSlash(String location) {
        return cutEndChar(location, "/");
    }

    /**
     * 递归去除目标字符串尾部字符。
     * "/a/b////",去除尾部"/"的话，结果为"/a/b"
     *
     * @param target  目标字符串；
     * @param cutChar 需要切除的尾部
     * @return 处理后的字符
     */
    public static String cutEndChar(String target, String cutChar) {
        if (target != null) {
            if (target.endsWith(cutChar)) {
                return cutEndChar(target.substring(0, target.length() - cutChar.length()), cutChar);
            } else {
                return target;
            }
        } else {
            return target;
        }
    }

}
