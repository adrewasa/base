package com.asa.base.utils.data.bytes;


import java.util.Arrays;

/**
 * Created by andrew_asa on 2017/7/20.
 */
public abstract class AbstractBytesBuilder implements BytesAppendable, BytesSequence {


    byte[] value = new byte[0];


    int count;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    AbstractBytesBuilder() {

    }

    AbstractBytesBuilder(int capacity) {

        value = new byte[capacity];
    }

    @Override
    public int length() {

        return count;
    }

    public int capacity() {

        return value.length;
    }

    public byte byteAt(int index) {

        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        return value[index];
    }

    /**
     * 设置某个位置的字节
     *
     * @param index
     * @param ch
     */
    public void setByteAt(int index, byte ch) {

        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        value[index] = ch;
    }


    /**
     * @param bytes
     * @param srcStart
     * @param destStart
     * @param len
     */
    public void setBytes(byte[] bytes, int srcStart, int destStart, int len) {

        if (bytes == null || bytes.length == 0) {
            return;
        }
        if (destStart < 0) {
            throw new IndexOutOfBoundsException("destStart=" + destStart);
        }
        if (srcStart < 0) {
            throw new IndexOutOfBoundsException("srcStart=" + srcStart);
        }
        if (len + destStart > length()) {
            throw new IndexOutOfBoundsException("start=" + srcStart + " len=" + len);
        }
        if (srcStart + len > bytes.length) {
            throw new IndexOutOfBoundsException("start=" + srcStart + " len=" + len);
        }
        System.arraycopy(bytes, srcStart, value, destStart, len);
        for (int i = srcStart, j = destStart, k = 0; k < len; i++, j++, k++) {
            setByteAt(j, bytes[i]);
        }
    }

    public void ensureCapacity(int minimumCapacity) {

        if (minimumCapacity > 0) {
            ensureCapacityInternal(minimumCapacity);
        }
    }

    /**
     * 往 dst里面添加当前的byte
     *
     * @param srcBegin
     * @param srcEnd
     * @param dst
     * @param dstBegin
     */
    public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {

        if (srcBegin < 0)
            throw new IndexOutOfBoundsException(String.valueOf(srcBegin));
        if ((srcEnd < 0) || (srcEnd > count))
            throw new IndexOutOfBoundsException(String.valueOf(srcEnd));
        if (srcBegin > srcEnd) {
            throw new IndexOutOfBoundsException("srcBegin > srcEnd");
        }
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    public byte[] getBytes() {

        byte[] ret = new byte[length()];
        if (count > 0) {
            System.arraycopy(value, 0, ret, 0, length());
        }
        return ret;
    }

    AbstractBytesBuilder append(AbstractBytesBuilder asb) {

        if (asb == null) {
            return this;
        }
        int len = asb.length();
        ensureCapacityInternal(count + len);
        asb.getBytes(0, len, value, count);
        count += len;
        return this;
    }

    public BytesAppendable append(BytesSequence bsq) {

        if (bsq == null) {
            return this;
        }
        if (bsq instanceof AbstractBytesBuilder) {
            return this.append((AbstractBytesBuilder) bsq);
        }
        return this.append(bsq, 0, bsq.length());
    }

    public AbstractBytesBuilder append(BytesSequence bsq, int start, int end) {

        if (bsq == null) {
            return this;
        }
        if ((start < 0) || (start > end) || (end > bsq.length())) {
            throw new IndexOutOfBoundsException(
                    "start " + start + ", end " + end + ", s.length() "
                            + bsq.length());
        }
        int len = end - start;
        ensureCapacityInternal(count + len);
        for (int i = start, j = count; i < end; i++, j++) {
            value[j] = bsq.byteAt(i);
        }
        count += len;
        return this;
    }

    //public AbstractBytesBuilder append(BytesBuffer sb) {
    //
    //    if (sb == null) {
    //        return this;
    //    }
    //    int len = sb.length();
    //    ensureCapacityInternal(count + len);
    //    sb.getBytes(0, len, value, count);
    //    count += len;
    //    return this;
    //}

    public AbstractBytesBuilder append(byte c) {

        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }

    public AbstractBytesBuilder append(short s) {

        this.append(BytesUtils.toByteArray(s));
        return this;
    }

    public AbstractBytesBuilder append(int i) {

        this.append(BytesUtils.toByteArray(i));
        return this;
    }

    public AbstractBytesBuilder append(long l) {

        this.append(BytesUtils.toByteArray(l));
        return this;
    }

    public AbstractBytesBuilder append(String s) {

        if (s != null) {
            this.append(s.getBytes());
        }
        return this;
    }


    public AbstractBytesBuilder append(byte[] bytes) {

        int len = bytes.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(bytes, 0, value, count, len);
        count += len;
        return this;
    }

    public AbstractBytesBuilder append(byte bytes[], int offset, int len) {

        if (len > 0) {
            ensureCapacityInternal(count + len);
        }
        System.arraycopy(bytes, offset, value, count, len);
        count += len;
        return this;
    }

    //BytesSequence subSequence(int start, int end) {
    //
    //    if (start < 0)
    //        throw new IndexOutOfBoundsException(String.valueOf(start));
    //    if (end > count)
    //        throw new IndexOutOfBoundsException(String.valueOf(end));
    //    if (start > end)
    //        throw new StringIndexOutOfBoundsException(end - start);
    //    return null;
    //}

    private void ensureCapacityInternal(int minimumCapacity) {
        // overflow-conscious code
        if (minimumCapacity - value.length > 0) {
            value = Arrays.copyOf(value,
                    newCapacity(minimumCapacity));
        }
    }

    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int newCapacity = (value.length << 1) + 2;
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0)
                ? hugeCapacity(minCapacity)
                : newCapacity;
    }

    private int hugeCapacity(int minCapacity) {

        if (Integer.MAX_VALUE - minCapacity < 0) { // overflow
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE)
                ? minCapacity : MAX_ARRAY_SIZE;
    }


}
