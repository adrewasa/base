package com.asa.base.applet.open;

import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;

/**
 * Created by asa on 2017/8/20.
 * 打开所在文件夹
 */
public class Open {

    public static void openInBrowser(String location) {

        String browser = getSystemBrowser();
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec(browser + " \"" + location + "\"");
            } else {
                Runtime.getRuntime().exec(new String[]{browser, location});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getSystemBrowser() {

        String systemBrowser = null;
        if (SystemUtils.IS_OS_WINDOWS) {
            systemBrowser = "explorer";
        } else if (SystemUtils.IS_OS_LINUX) {
            // TODO
        } else if (SystemUtils.IS_OS_MAC) {
            systemBrowser = "open";
        }
        return systemBrowser;
    }
}
