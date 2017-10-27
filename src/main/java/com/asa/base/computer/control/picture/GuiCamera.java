package com.asa.base.computer.control.picture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by andrew_asa on 2017/7/18.
 * 屏幕相关
 */
public class GuiCamera {

    private String fileName;

    private String defalutName = "asa.picture";

    private String imageFormat;

    private String defalutImageFormat = "png";

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    public GuiCamera() {

        fileName = defalutName;
        imageFormat = defalutImageFormat;
    }

    public GuiCamera(String fileName, String imageFormat) {

        this.fileName = fileName;
        this.imageFormat = imageFormat;
    }

    public GuiCamera(String fileName) {

        this.fileName = fileName;
    }

    public void snapShot() {

        try {
            //拷贝屏幕到一个BufferedImage对象screenshot
            BufferedImage screenshot = (new Robot()).createScreenCapture(new Rectangle(0, 0, (int) dimension.getWidth(), (int) dimension.getHeight()));
            //根据文件前缀变量和文件格式变量，自动生成文件名
            String name = fileName + "." + imageFormat;
            File f = new File(name);
            System.out.print("Save File " + name);
            //将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, f);
            System.out.print("..Finished!\n");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
