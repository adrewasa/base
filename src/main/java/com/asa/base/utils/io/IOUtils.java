package com.asa.base.utils.io;


import com.asa.base.utils.data.GeneralUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by andrew_asa on 2017/7/27.
 */
public class IOUtils {

    /**
     * 安静关闭可以进行关闭的类
     *
     * @param cs
     */
    public static void closeQuietly(Closeable... cs) {

        if (cs != null) {
            for (Closeable o : cs) {
                if (o != null) {
                    try {
                        o.close();
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    /**
     * 二进制复制
     *
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyBinaryToQuietly(InputStream in, OutputStream out) {

        if (GeneralUtils.allNotNull(in, out)) {
            try {
                byte[] buf = new byte[10240];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.flush();
            } catch (IOException var4) {
                ;
            }
        }
    }


}
