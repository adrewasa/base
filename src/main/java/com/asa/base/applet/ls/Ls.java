package com.asa.base.applet.ls;

import java.io.File;

/**
 * Created by andrew_asa on 2017/7/23.
 */
public class Ls {

    private String dir = "./";

    public Ls(String dir, String[] args) {

        this.dir = dir;
        parseParameter(args);
    }

    public Ls(String[] args) {

        parseParameter(args);
    }

    public Ls(String dir) {

        this.dir = dir;
    }

    public Ls() {

    }

    private void parseParameter(String[] args) {

    }

    /**
     * 简单列出文件
     */
    public void simpleLs() {

        File f = new File("");
        File p = f.getParentFile();

    }

    public LsNode getSimpleLsNode() throws Exception {

        LsNode pn = null;
        File pf = new File(dir);
        pn = new LsNode(pf);
        // 文件夹的时候需要进行列出其子孙
        if (pf.isDirectory()) {
            for (File f : pf.listFiles()) {
                LsNode c = new LsNode(f);
                pn.addChild(c);
            }
        }
        return pn;
    }

    public static void printfLsNode(LsNode ls) {

        if (ls != null) {
            if (ls.getType() == LsConstant.LSNODE_TYPE_DIR) {
                System.out.print(ls.getName());
                System.out.println();
                for (LsNode n : ls.getChild()) {
                    System.out.print(n.getName());
                    System.out.print("    ");
                }
            } else if (ls.getType() == LsConstant.LSNODE_TYPE_FILE) {
                System.out.println(ls.getName());
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Ls ls = new Ls("F:\\new\\music");
        LsNode lsNode = ls.getSimpleLsNode();
        byte[] bytes = lsNode.toBytes();
        LsNode node = new LsNode();
        node.parse(bytes, 0, bytes.length);

        Ls.printfLsNode(node);
        Ls.printfLsNode(lsNode);
    }
}
