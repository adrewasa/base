package com.asa.base.applet.btscan;

import com.asa.base.utils.data.bool.BooleanUtils;
import com.asa.base.utils.data.bytes.BytesUtils;
import com.asa.base.utils.data.bytes.CharsUtils;
import com.asa.base.utils.net.IPUtils;

import java.net.InetAddress;

/**
 * Created by andrew_asa on 2017/7/20.
 */
public class NetBiosResponse {

    /**
     * 相应头
     */
    private NbNameResponseHeader header;

    /**
     * 名字 用户名/公司名等
     */
    private NBName[] names;

    /**
     * 响应尾,包含mac地址等
     */
    private NBNameResponseFooter footer;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 公司名
     */
    private String companyName;

    public String getUserName() {

        return userName;
    }

    public String getCompanyName() {

        return companyName;
    }

    public String getServiceStr() {

        return serviceStr;
    }

    public boolean isIs_server() {

        return is_server;
    }

    public byte[] getMapAddr() {

        return mapAddr;
    }

    public int getIs_broken() {

        return is_broken;
    }

    private String serviceStr;

    boolean is_server = false;

    /**
     * mac地址
     */
    byte[] mapAddr;

    public NbNameResponseHeader getHeader() {

        return header;
    }

    public NBName[] getNames() {

        return names;
    }

    public NBNameResponseFooter getFooter() {

        return footer;
    }

    public InetAddress getDst() {

        return dst;
    }

    /**
     * 目标地址
     */
    InetAddress dst;

    private int is_broken;

    private boolean isParse = false;

    public NetBiosResponse(final byte[] resp, int size, InetAddress dst) {

        this.dst = dst;
        int offset = 0;
        header = new NbNameResponseHeader();
        int ret;
        if ((ret = header.parse(resp, offset, size)) > 0) {
            is_broken = ret;
            return;
        }
        offset += header.size();
        int namesSize = header.getNumber_of_names();
        names = new NBName[namesSize];
        for (int i = 0; i < namesSize; i++) {
            names[i] = new NBName();
            if ((ret = (names[i].parse(resp, offset, size))) > 0) {
                is_broken = ret;
                return;
            }
            offset += names[i].size();
        }
        footer = new NBNameResponseFooter();
        if ((ret = footer.parse(resp, offset, size)) > 0) {
            is_broken = ret;
            return;
        }
    }

    /**
     * 显示
     */
    public void print() {

        //if (!isParse) {
        //    beforePrint();
        //}
        //192.168.1.101    ANDREW           <server>  <unknown>        4dcd6f478f44
        //System.out.printf("%-17s%-17s", dst.getHostAddress(), companyName);
        //System.out.printf("%-10s", serviceStr);
        //System.out.printf("%-17s", userName);
        //System.out.println(IPUtils.byteArrayToMacString(mapAddr));

        System.out.println(getFormatStr());
        System.out.flush();
    }

    public String getFormatStr() {

        if (!isParse) {
            beforePrint();
        }
        return String.format("%-17s%-17s%-10s%-17s%-17s", dst.getHostAddress(), companyName, serviceStr, userName, IPUtils.byteArrayToMacString(mapAddr));
    }

    public String getLocalStrIp() {

        return dst.getHostAddress();
    }

    public String getMacStr() {

        return IPUtils.byteArrayToMacString(mapAddr);
    }

    /**
     * 详细打印
     */
    public void printVerbose() {

    }

    private void beforePrint() {

        byte service;
        char[] comp_name = new char[16];
        char[] user_name = new char[16];
        CharsUtils.paddingChar(comp_name, ' ');
        CharsUtils.paddingChar(user_name, ' ');
        boolean unique;
        boolean contain_comp_name = false;
        boolean contain_user_name = false;
        boolean first_name = true;
        int nameSize = header.getNumber_of_names();
        for (int i = 0; i < nameSize; i++) {
            NBName n = names[i];
            byte[] asciiName = n.getAscii_name();
            service = asciiName[15];
            unique = BooleanUtils.isTrue((byte) (~(n.getRr_flags() & 0x80)));
            // 0000 0000
            if (service == 0x00 && unique && first_name) {
                char[] t = BytesUtils.byteArrayToCharArray(asciiName, 1);
                contain_comp_name = true;
                System.arraycopy(t, 0, comp_name, 0, 15);
                companyName = new String(comp_name);
                first_name = false;
            }
            // 0010 0000
            if (service == 0x20 && unique) {
                serviceStr = "<server>";
                is_server = true;
            }
            // 0000 0011
            if (service == 0x03) {
                char[] t = BytesUtils.byteArrayToCharArray(asciiName, 1);
                System.arraycopy(t, 0, user_name, 0, 15);
                contain_user_name = true;
                userName = new String(user_name);
            }
        }
        if (!contain_comp_name) {
            CharsUtils.paddingCharArray(comp_name, "<unknown>".toCharArray());
            companyName = "<unknown>";
        }
        if (!contain_user_name) {
            CharsUtils.paddingCharArray(user_name, "<unknown>".toCharArray());
            userName = "<unknown>";
        }
        mapAddr = footer.getAdapter_address();
        // 标志为已经进行解析过了
        isParse = true;
    }
}
