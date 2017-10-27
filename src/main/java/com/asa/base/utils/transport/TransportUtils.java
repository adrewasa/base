package com.asa.base.utils.transport;

import java.math.BigDecimal;

/**
 * Created by andrew_asa on 2017/8/18.
 * 传输相关的，如计算传输速率什么的
 */
public class TransportUtils {

    /**
     * 接收百分比
     *
     * @param rev
     * @param total
     * @return
     */
    public static double revPercent(long rev, long total) {

        BigDecimal v1 = new BigDecimal(rev);
        BigDecimal v2 = new BigDecimal(total);
        BigDecimal v3 = new BigDecimal(100);
        return (v1.doubleValue() / v2.doubleValue()) * v3.doubleValue();
    }

    public static double transportRateKb(long t1, long t2, long fz) {

        BigDecimal v1 = new BigDecimal(fz * 1000);
        BigDecimal v2 = new BigDecimal((1024 * (t2 - t1)));
        return v1.doubleValue() / v2.doubleValue();
        //return (fz * (t2 - t1) / (1024 * 1000));
    }

    public static double transportRateMb(long t1, long t2, long fz) {

        BigDecimal v1 = new BigDecimal(fz * 1000);
        BigDecimal v2 = new BigDecimal((1024 * 1024 * (t2 - t1)));
        return v1.doubleValue() / v2.doubleValue();
    }

    public static double fileSizeKb(long s) {

        BigDecimal v1 = new BigDecimal(s);
        BigDecimal v2 = new BigDecimal(1024);
        return v1.doubleValue() / v2.doubleValue();
    }

    public static double fileSizeMb(long s) {

        BigDecimal v1 = new BigDecimal(s);
        BigDecimal v2 = new BigDecimal(1024 * 1024);
        return v1.doubleValue() / v2.doubleValue();
    }

    public static double fileSizeGb(long s) {

        BigDecimal v1 = new BigDecimal(s);
        BigDecimal v2 = new BigDecimal(1024 * 1024 * 1024);
        return v1.doubleValue() / v2.doubleValue();
    }

    public static double costSecond(long t1, long t2) {

        BigDecimal v1 = new BigDecimal(t2 - t1);
        BigDecimal v2 = new BigDecimal(1000);
        return v1.doubleValue() / v2.doubleValue();
    }
}
