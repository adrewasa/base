package com.asa.base.computer.control.picture.action;


import com.asa.base.computer.control.picture.ScreenUtils;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

/**
 * Created by andrew_asa on 2017/7/19.
 */
public class MouseUtils {

    public static void mourseWheel(Point point) {

        try {

            Dimension d = ScreenUtils.getScreenDimension();
            Robot robot = new Robot();

            int lx = point.x;
            int ly = point.y;

            int rx = (int) d.getWidth();
            int ry = (int) d.getHeight();

            int xs = (rx - lx) / 50;
            int ys = (ry - ly) / 50;

            int del = 3 * 1000 / 50;

            while ((lx < rx)) {
                lx += xs;
                ly += ys;
                robot.mouseMove(lx, ly);
                robot.mouseWheel(2);
                //robot.mousePress(InputEvent.BUTTON1_MASK);
                //robot.mouseRelease(InputEvent.BUTTON1_MASK);

                robot.delay(del);
            }
            for (int i = 0; i < 100; i++) {
                robot.mouseWheel(40);
                robot.delay(300);
            }


        } catch (Exception e) {

        }
    }

    public static Point getCurrentMousePoint() {

        return MouseInfo.getPointerInfo().getLocation();
    }
}
