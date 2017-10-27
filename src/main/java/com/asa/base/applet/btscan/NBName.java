package com.asa.base.applet.btscan;


import com.asa.base.utils.data.bytes.BytesUtils;

/**
 * Created by andrew_asa on 2017/7/21.
 */
public class NBName implements Parse, Measurable {

    byte[] ascii_name;

    int rr_flags;

    public byte[] getAscii_name() {

        return ascii_name;
    }

    public int getRr_flags() {

        return rr_flags;
    }

    @Override
    public int parse(final byte[] bytes, int start, int len) {

        int offset = start;
        if (offset + 16 > len) {
            return offset;
        }
        ascii_name = new byte[16];
        System.arraycopy(bytes, start, ascii_name, 0, 16);
        offset += 16;

        if (offset + 2 > len) {
            return offset;
        }
        rr_flags = BytesUtils.byteArrayToInt(bytes, offset, 2);
        return 0;
    }

    @Override
    public int size() {

        return BTScanConstant.NBNAME_RESPONSE_NAME_SIZE;
    }
}

