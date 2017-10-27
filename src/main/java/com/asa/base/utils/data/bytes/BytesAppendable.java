package com.asa.base.utils.data.bytes;


import java.io.IOException;

/**
 * Created by andrew_asa on 2017/7/20.
 */
public interface BytesAppendable {

    /**
     * 添加
     *
     * @param bsq
     * @return
     * @throws IOException
     */
    BytesAppendable append(BytesSequence bsq) throws IOException;

    /**
     * 添加
     *
     * @param bsq
     * @param start
     * @param end
     * @return
     * @throws IOException
     */
    BytesAppendable append(BytesSequence bsq, int start, int end) throws IOException;

    /**
     * 添加一个字节
     *
     * @param b
     * @return
     * @throws IOException
     */
    BytesAppendable append(byte b) throws IOException;




    /**
     * 添加byte数组
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    BytesAppendable append(byte[] bytes) throws IOException;

    /**
     * 添加byte数组
     *
     * @param bytes
     * @param offset
     * @param len
     * @return
     */
    BytesAppendable append(byte bytes[], int offset, int len);

}
