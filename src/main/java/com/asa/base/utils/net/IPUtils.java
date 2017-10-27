package com.asa.base.utils.net;


import com.asa.base.utils.data.string.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew_asa on 2017/7/19.
 */
public class IPUtils {

    /**
     * 获取本机局域网ip地址 字符串形式
     *
     * @return
     */
    public static String getLocalIp() {

        try {
            return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取本电脑所在的局域网网段
     *
     * @return
     */
    public static List<String> getLocalIpSegment(int start, int end) {

        List<String> ret = new ArrayList<String>();
        if (start < 0 || end < 0 || start > end) {
            return ret;
        }
        String localIp = getLocalIp();
        if (StringUtils.isNotEmpty(localIp)) {
            //try {
            //InetAddress address = InetAddress.getByName(localIp);
            String sp = localIp.substring(0, localIp.lastIndexOf('.') + 1);
            for (int i = start; i < end; i++) {
                ret.add(sp + i);
            }
            //String[] n = localIp.split("\\.");
            //if (n != null && n.length == 4) {
            //    int r = Integer.parseInt(n[3]);
            //}
            //} catch (UnknownHostException e) {
            //    e.printStackTrace();
            //}
        }
        return ret;
    }

    public static void main(String[] args) {

        List<String> ips = getLocalIpSegment(1, 255);
        for (String s : ips) {
            System.out.println(s);
        }
    }

    public static char hexUpperChar(byte b) {

        b = (byte) ((b >> 4) & 0xf);
        if (b == 0) {
            return '0';
        } else if (b < 10) {
            return (char) ('0' + b);
        } else {
            return (char) ('a' + b - 10);
        }
    }

    public static char hexLowerChar(byte b) {

        b = (byte) (b & 0xf);
        if (b == 0) {
            return '0';
        } else if (b < 10) {
            return (char) ('0' + b);
        } else {
            return (char) ('a' + b - 10);
        }
    }

    public static String byteMacAddrToStrAddr(byte[] mac, char split) {

        char[] adr = new char[17];
        for (int i = 0; i < 5; i++) {
            adr[i * 3] = hexUpperChar(mac[i]);
            adr[i * 3 + 1] = hexLowerChar(mac[i]);
            adr[i * 3 + 2] = split;
        }
        adr[15] = hexUpperChar(mac[5]);
        adr[16] = hexLowerChar(mac[5]);
        return new String(adr);
    }

    public static String byteMacAddrToStrAddr(byte[] mac) {

        return byteMacAddrToStrAddr(mac, ':');
    }

    public static String getMacByIp(String ip) {

        try {
            NetworkInterface ne = null;
            if (StringUtils.isEmpty(ip)) {
                ne = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

            } else {
                ne = NetworkInterface.getByInetAddress(InetAddress.getByName(ip));
            }
            byte[] mac = ne.getHardwareAddress();
            return byteMacAddrToStrAddr(mac);
        } catch (Exception e) {
        }
        return "";
    }

    public static String byteArrayToMacString(byte[] bytes) {

        if (bytes != null && bytes.length == 6) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 5; i++) {
                sb.append(Integer.toHexString(0xFF & bytes[i]));
                sb.append(":");
            }
            sb.append(Integer.toHexString(0xFF & bytes[5]));
            return sb.toString();
        }
        return "";
    }
}
