package com.asa.base.utils.data.bytes;

/**
 * Created by andrew_asa on 2017/7/20.
 */
public class BytesUtils {

    private BytesUtils() {

    }

    public static byte[] toByteArray(final long value) {

        return new byte[]{
                (byte) (value >>> 56),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    /**
     * 整数转byte数组
     *
     * @param value
     * @return
     */
    public static byte[] toByteArray(final int value) {

        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    public static byte[] toByteArray(final short value) {

        return new byte[]{
                (byte) (value >>> 8),
                (byte) value,
        };
    }

    /**
     * char转byte[]
     *
     * @param c
     * @return
     */
    public static byte[] toByteArray(final char c) {

        return new byte[]{
                (byte) (c >>> 8),
                (byte) c
        };
    }

    public static byte[] toByteArray(final char c, int l) {

        if (l == 2) {
            return toByteArray(c);
        } else if (l == 1) {
            return new byte[]{
                    (byte) c
            };
        } else {
            return new byte[0];
        }
    }

    /**
     * 整数转byte数组
     *
     * @param value
     * @param l
     * @return
     */
    public static byte[] toByteArray(final int value, int l) {

        if (l == 4) {
            return toByteArray(value);
        }
        if (l <= 0 || l > 4) {
            return new byte[0];
        }
        byte[] r = new byte[l];
        int s = 0;
        for (int i = l - 1; i >= 0; i--) {
            r[i] = (byte) (value >>> s);
            s += 8;
        }
        return r;
    }

    /**
     * byte数组转int
     *
     * @param byteArray
     * @return
     */
    public static int byteArrayToInt(final byte[] byteArray) {

        if (byteArray == null) {
            throw new IllegalArgumentException("Parameter 'byteArray' cannot be null");
        }
        return byteArrayToInt(byteArray, 0, byteArray.length);
    }

    /**
     * byte[]转char
     *
     * @param bytes
     * @return
     */
    public static char byteArrayToChar(final byte[] bytes) {

        if (bytes == null) {
            return '\0';
        } else if (bytes.length == 1) {
            return (char) bytes[0];
        } else {
            return (char) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
        }
    }

    /**
     * byte[]转char[]
     *
     * @param bytes
     * @return
     */
    public static char[] byteArrayToCharArray(final byte[] bytes) {

        if (bytes == null) {
            return new char[0];
        }
        String s = new String(bytes);
        return s.toCharArray();
    }

    /**
     * byte[]转char[]
     *
     * @param bytes
     * @return
     */
    public static char[] byteArrayToCharArray(final byte[] bytes, int l) {

        if (bytes == null) {
            return new char[0];
        }

        if (l == 2) {
            return byteArrayToCharArray(bytes);
        }
        if (l == 1) {
            //byte[] nbytes = new byte[bytes.length * 2];
            char[] ret = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                //nbytes[i] = bytes
                ret[i] = (char) bytes[i];
            }
            return ret;
        }
        return new char[0];
    }

    /**
     * byte数组转short
     *
     * @param byteArray
     * @param startPos
     * @param length
     * @return
     */
    public static short byteArrayToShort(final byte[] byteArray, final int startPos, final int length) {

        if (byteArray == null) {
            throw new IllegalArgumentException("Parameter 'byteArray' cannot be null");
        }
        if (length <= 0 || length > 2) {
            throw new IllegalArgumentException("Length must be between 1 and 2. Length = " + length);
        }
        if (startPos < 0 || byteArray.length < startPos + length) {
            throw new IllegalArgumentException("Length or startPos not valid");
        }
        short value = 0;
        for (int i = 0; i < length; i++) {
            value += (byteArray[startPos + i] & 0xFF) << 8 * (length - i - 1);
        }
        return value;
    }

    public static short byteArrayToShort(final byte[] bytes) {

        if (bytes == null) {
            throw new IllegalArgumentException("Parameter 'byteArray' cannot be null");
        }
        if (bytes.length == 1) {
            return (short) bytes[0];
        }
        short value = 0;
        value += (bytes[0] & 0xFF) << 8;
        value += bytes[1] & 0xFF;
        return value;
    }

    /**
     * byte数组转int
     *
     * @param byteArray
     * @param startPos
     * @param length
     * @return
     */
    public static int byteArrayToInt(final byte[] byteArray, final int startPos, final int length) {

        if (byteArray == null) {
            throw new IllegalArgumentException("Parameter 'byteArray' cannot be null");
        }
        if (length <= 0 || length > 4) {
            throw new IllegalArgumentException("Length must be between 1 and 4. Length = " + length);
        }
        if (startPos < 0 || byteArray.length < startPos + length) {
            throw new IllegalArgumentException("Length or startPos not valid");
        }
        int value = 0;
        for (int i = 0; i < length; i++) {
            value += (byteArray[startPos + i] & 0xFF) << 8 * (length - i - 1);
        }
        return value;
    }


    /**
     * byte数组转long
     *
     * @param byteArray
     * @param startPos
     * @param length
     * @return
     */
    public static long byteArrayToLong(final byte[] byteArray, final int startPos, final int length) {

        if (byteArray == null) {
            throw new IllegalArgumentException("Parameter 'byteArray' cannot be null");
        }
        if (length <= 0 || length > 8) {
            throw new IllegalArgumentException("Length must be between 1 and 8. Length = " + length);
        }
        if (startPos < 0 || byteArray.length < startPos + length) {
            throw new IllegalArgumentException("Length or startPos not valid");
        }
        long value = 0;
        for (int i = 0; i < length; i++) {
            value += (byteArray[startPos + i] & 0xFF) << 8 * (length - i - 1);
        }
        return value;
    }

    public static byte not(byte b) {

        return (byte) ~b;
    }


    public static void main(String args[]) {

        byte[] a = {'v', 'a', 'b', 'd'};
        char[] c = byteArrayToCharArray(a, 1);
        short d = 5563;
        byte[] e = toByteArray(d);
        System.out.println(d);
        System.out.println(byteArrayToShort(e));
        System.out.println(byteArrayToShort(e, 0, 2));
        //System.out.println(a);
        //byte[] b = new byte[a.length];
        //for (int i = 0; i < b.length; i++) {
        //    b[i] = toByteArray(a[i], 1)[0];
        //}
        //char[] c = new char[a.length];
        //for (int i = 0; i < a.length; i++) {
        //    c[i] = byteArrayToChar(new byte[]{b[i]});
        //}
        //System.out.println(c);
    }

}
