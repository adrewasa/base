package com.asa.base.utils.data.bytes;

/**
 * Created by andrew_asa on 2017/7/20.
 */
public interface BytesSequence {

    int length();


    byte byteAt(int index);


    BytesSequence subSequence(int start, int end);
}
