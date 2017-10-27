package com.asa.base.utils.data;

/**
 * Created by andrew_asa on 2017/7/28.
 * 通用工具类
 */
public class GeneralUtils {

    /**
     * 参数全不为null
     *
     * @param os
     * @return
     */
    public static boolean allNotNull(Object... os) {

        for (int i = 0; i < os.length; i++) {
            if (os[i] == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean containNull(Object... os) {

        for (int i = 0; i < os.length; i++) {
            if (os[i] == null) {
                return true;
            }
        }
        return false;
    }
}
