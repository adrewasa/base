package com.asa.base.applet.btscan;

/**
 * Created by andrew_asa on 2017/7/21.
 */

/**
 * 解析
 */
interface Parse {

    /**
     * 解析
     *
     * @param bytes
     * @param start
     * @param len
     * @return
     */
    int parse(final byte[] bytes, int start, int len);
}
