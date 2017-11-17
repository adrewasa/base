package com.asa.base.ui;

import javax.swing.*;
import java.io.File;

/**
 * Created by andrew_asa on 2017/7/30.
 */
public class UIUtils {

    private UIUtils() {

    }

    /**
     * 选择文件
     *
     * @return
     */
    public static File chooseFile() {

        return chooseSelect(JFileChooser.FILES_ONLY, "选择文件");
    }

    public static File chooseDir() {

        return chooseSelect(JFileChooser.DIRECTORIES_ONLY, "选择文件夹");
    }


    /**
     * 选择文件和文件夹
     *
     * @return
     */
    public static File chooseFileAndDir() {

        return chooseSelect(JFileChooser.FILES_AND_DIRECTORIES, "选择文件/文件夹");
    }

    public static File chooseSelect(int mode, String text) {

        return chooseSelect(mode, text, null);
    }

    public static File chooseSelect(int mode, String text, File currentDir) {

        JFileChooser jfc = new JFileChooser();
        if (currentDir != null && currentDir.isDirectory()) {
            jfc.setCurrentDirectory(currentDir);
        }
        jfc.setFileSelectionMode(mode);
        jfc.showDialog(new JLabel(), text);
        File file = jfc.getSelectedFile();
        return file;
    }
}
