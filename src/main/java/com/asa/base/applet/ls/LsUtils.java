package com.asa.base.applet.ls;

/**
 * Created by andrew_asa on 2017/7/24.
 */
public class LsUtils {

    public static String nodeTypeToString(byte type) {

        if (type == LsConstant.LSNODE_TYPE_DIR) {
            return "dir";
        } else if (type == LsConstant.LSNODE_TYPE_FILE) {
            return "file";
        } else {
            return "unknow";
        }
    }
}
