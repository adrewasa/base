package com.asa.base.applet.btscan;

import com.asa.base.utils.data.bytes.BytesUtils;

/**
 * 响应头
 * Created by andrew_asa on 2017/7/21.
 */
public class NbNameResponseHeader implements Parse, Measurable {

    private int transaction_id;

    private int flags;

    private int question_count;

    private int answer_count;

    private int name_service_count;

    private int additional_record_count;

    byte[] question_name;

    private int question_type;

    private int question_class;

    private long ttl;

    private int rdata_length;

    short number_of_names;

    public int getTransaction_id() {

        return transaction_id;
    }

    public int getFlags() {

        return flags;
    }

    public int getQuestion_count() {

        return question_count;
    }

    public int getAnswer_count() {

        return answer_count;
    }

    public int getName_service_count() {

        return name_service_count;
    }

    public int getAdditional_record_count() {

        return additional_record_count;
    }

    public byte[] getQuestion_name() {

        return question_name;
    }

    public int getQuestion_type() {

        return question_type;
    }

    public int getQuestion_class() {

        return question_class;
    }

    public long getTtl() {

        return ttl;
    }

    public int getRdata_length() {

        return rdata_length;
    }

    public short getNumber_of_names() {

        return number_of_names;
    }

    @Override
    public int parse(final byte[] bytes, int start, int len) {

        // 数据不够构造一个Header对象
        //if (start + size() > len) {
        //    return false;
        //}
        int offset = start;
        if (offset + 2 > len) {
            return offset;
        }
        transaction_id = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;
        if (offset + 2 > len) {
            return offset;
        }
        flags = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        question_count = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        answer_count = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        name_service_count = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        additional_record_count = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 34 > len) {
            return offset;
        }
        question_name = new byte[34];
        System.arraycopy(bytes, offset, question_name, 0, 34);
        offset += 34;

        if (offset + 2 > len) {
            return offset;
        }
        question_type = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        question_class = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 4 > len) {
            return offset;
        }
        ttl = BytesUtils.byteArrayToLong(bytes, offset, 4);
        offset += 4;

        if (offset + 2 > len) {
            return offset;
        }
        rdata_length = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 1 > len) {
            return offset;
        }
        number_of_names = BytesUtils.byteArrayToShort(bytes, offset, 1);
        return 0;
    }

    @Override
    public int size() {

        return BTScanConstant.NBNAME_RESPONSE_HEADER_SIZE;
    }
}

