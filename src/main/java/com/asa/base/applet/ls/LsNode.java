package com.asa.base.applet.ls;


import com.asa.base.utils.data.bytes.BytesBuffer;
import com.asa.base.utils.data.bytes.BytesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew_asa on 2017/7/23.
 */
public class LsNode {

    List<LsNode> child = new ArrayList<LsNode>();

    /**
     * 文件或目录名
     */
    String name;

    byte type;

    public void setName(String name) {

        this.name = name;
    }

    public void setType(byte type) {

        this.type = type;
    }

    File f;

    public LsNode() {

    }

    public LsNode(String name, byte type) {

        this.name = name;
        this.type = type;
    }

    public LsNode(File f) throws Exception {

        if (f != null) {
            this.f = f;

            this.name = new String(f.getAbsolutePath().getBytes(), "utf-8");

            if (f.isDirectory()) {
                setType(LsConstant.LSNODE_TYPE_DIR);
            } else {
                setType(LsConstant.LSNODE_TYPE_FILE);
            }
        }
    }

    public void addChild(LsNode n) {

        if (n != null) {
            child.add(n);
        }
    }

    public List<LsNode> getChild() {

        return child;
    }

    public String getName() {

        return name;
    }

    public byte getType() {

        return type;
    }

    /**
     * 字节化
     * 并不是所有的属性都进行字节化目前字节化的有
     * 名字
     * <p>
     * 理论上是可以支持多个层次的
     *
     * @return
     */
    public byte[] toBytes() throws Exception{

        byte[] names = name.getBytes("utf-8");
        short nl = (short) names.length;
        short cl = (short) child.size();
        BytesBuffer bb = new BytesBuffer();

        bb.append(type);
        bb.append(nl);
        bb.append(cl);
        // 名字
        bb.append(names);

        for (LsNode n : child) {
            bb.append(n.toBytes());
        }
        return bb.getBytes();
    }

    /**
     * 慎用
     *
     * @return
     */
    public int size() throws Exception{

        return toBytes().length;
    }

    public int parse(final byte[] bytes, int start, int len) throws Exception {

        int offset = start;
        if (offset + 1 > len) {
            return offset;
        }
        // 类型
        byte type = bytes[offset++];

        if (offset + 2 > len) {
            return offset;
        }
        // 名字长度
        short nl = BytesUtils.byteArrayToShort(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        // 儿子节点长度
        short cl = BytesUtils.byteArrayToShort(bytes, offset, 2);
        offset += 2;

        if (offset + nl > len) {
            return offset;
        }
        byte[] nb = new byte[nl];
        System.arraycopy(bytes, offset, nb, 0, nl);
        // 名字
        String name = new String(nb, "utf-8");
        offset += nl;

        setType(type);
        setName(name);

        // 儿子
        for (int i = 0; i < cl; i++) {
            LsNode c = new LsNode();
            if (c.parse(bytes, offset, len) == 0) {
                addChild(c);
                offset += c.size();
            } else {
                return offset;
            }
        }
        return 0;
    }

    public boolean isFile() {

        return type == LsConstant.LSNODE_TYPE_FILE;
    }

    public boolean isDir() {

        return type == LsConstant.LSNODE_TYPE_DIR;
    }
}
