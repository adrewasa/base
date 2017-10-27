package com.asa.base.utils.data.bytes;

import com.asa.utils.data.bytes.BytesSequence;

/**
 * Created by andrew_asa on 2017/7/20.
 */
public class BytesBuffer extends AbstractBytesBuilder implements BytesSequence {

    private static int DEFAULTCAPACITY = 16;

    public BytesBuffer() {

        super(DEFAULTCAPACITY);
    }

    public BytesBuffer(int capacity) {

        super(capacity);
    }

    public BytesBuffer(byte[] bytes) {

        super(DEFAULTCAPACITY);
        this.append(bytes);
    }

    public BytesBuffer(BytesSequence bsq) {

        super(DEFAULTCAPACITY);
        this.append(bsq);
    }

    @Override
    public BytesSequence subSequence(int start, int end) {

        if (start < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(start));
        }
        if (end > count) {
            throw new IndexOutOfBoundsException(String.valueOf(end));
        }
        if (start > end) {
            throw new IndexOutOfBoundsException("end - start <0");
        }
        BytesBuffer ret = new BytesBuffer();
        ret.append(this, start, end);
        return ret;
    }

    public BytesBuffer append(BytesBuffer bsq) {

        super.append(bsq);
        return this;
    }

    @Override
    public BytesBuffer append(BytesSequence bsq, int start, int end) {

        super.append(bsq, start, end);
        return this;
    }

    @Override
    public BytesBuffer append(byte b) {

        super.append(b);
        return this;
    }

    public BytesBuffer append(short s) {

        super.append(s);
        return this;
    }

    public BytesBuffer append(int i) {

        super.append(i);
        return this;
    }

    public BytesBuffer append(long l) {

        super.append(l);
        return this;
    }

    public BytesBuffer append(String s) {

        super.append(s);
        return this;
    }

    @Override
    public BytesBuffer append(byte[] bytes) {

        super.append(bytes);
        return this;
    }

    @Override
    public BytesBuffer append(byte[] bytes, int offset, int len) {

        super.append(bytes, offset, len);
        return this;
    }
}
