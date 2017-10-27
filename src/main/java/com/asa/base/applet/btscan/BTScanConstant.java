package com.asa.base.applet.btscan;

/**
 * Created by andrew_asa on 2017/7/19.
 */
public class BTScanConstant {

    public static int FL_BROADCAST = 0x0010;


    /**
     * NetBios 协议端口
     */
    public static int NET_BIOS_PORT = 137;

    /**
     * 响应头大小
     */
    public static int NBNAME_RESPONSE_HEADER_SIZE = 57;

    /**
     * 响应中间报文每个名字的长度
     */
    public static int NBNAME_RESPONSE_NAME_SIZE = 18;

    /**
     * 响应未大小
     */
    public static int NBNAME_RESPONSE_FOOTER_SIZE = 50;

    /**
     * 缓存数据大小
     */
    public static int BUF_DATA_LEN = 1024;

    /**
     * 等待延时时间
     */
    public static int TIME_OUT = 500;

    /**
     * 出错尝试次数
     */
    public static int TRIES_TIME = 2;

    /**
     * 放入select中进行监视的长度
     */
    public static int SELECT_WATCH_LEN = 100;


}
