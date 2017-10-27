package com.asa.base.computer.control.picture;

import java.awt.*;

/**
 * Created by andrew_asa on 2017/7/19.
 */
public class ScreenUtils {

    /**
     * 获取屏幕区域
     * @return
     */
    public static Dimension getScreenDimension() {

        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
