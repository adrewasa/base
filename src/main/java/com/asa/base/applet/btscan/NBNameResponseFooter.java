package com.asa.base.applet.btscan;

import com.asa.base.utils.data.bytes.BytesUtils;

/**
 * Created by andrew_asa on 2017/7/21.
 */
public class NBNameResponseFooter implements Parse, Measurable {

    /**
     * mac 地址
     */
    private byte[] adapter_address;

    private short version_major;

    private short version_minor;

    private int duration;

    private int frmps_received;

    private int frmps_transmitted;

    private int iframe_receive_errors;

    private int transmit_aborts;

    private long transmitted;

    private long received;

    private int iframe_transmit_errors;

    private int no_receive_buffer;

    private int tl_timeouts;

    private int ti_timeouts;

    private int free_ncbs;

    private int ncbs;

    private int max_ncbs;

    private int no_transmit_buffers;

    private int max_datagram;

    private int pending_sessions;

    private int max_sessions;

    private int packet_sessions;

    public byte[] getAdapter_address() {

        return adapter_address;
    }

    public short getVersion_major() {

        return version_major;
    }

    public short getVersion_minor() {

        return version_minor;
    }

    public int getDuration() {

        return duration;
    }

    public int getFrmps_received() {

        return frmps_received;
    }

    public int getFrmps_transmitted() {

        return frmps_transmitted;
    }

    public int getIframe_receive_errors() {

        return iframe_receive_errors;
    }

    public int getTransmit_aborts() {

        return transmit_aborts;
    }

    public long getTransmitted() {

        return transmitted;
    }

    public long getReceived() {

        return received;
    }

    public int getIframe_transmit_errors() {

        return iframe_transmit_errors;
    }

    public int getNo_receive_buffer() {

        return no_receive_buffer;
    }

    public int getTl_timeouts() {

        return tl_timeouts;
    }

    public int getTi_timeouts() {

        return ti_timeouts;
    }

    public int getFree_ncbs() {

        return free_ncbs;
    }

    public int getNcbs() {

        return ncbs;
    }

    public int getMax_ncbs() {

        return max_ncbs;
    }

    public int getNo_transmit_buffers() {

        return no_transmit_buffers;
    }

    public int getMax_datagram() {

        return max_datagram;
    }

    public int getPending_sessions() {

        return pending_sessions;
    }

    public int getMax_sessions() {

        return max_sessions;
    }

    public int getPacket_sessions() {

        return packet_sessions;
    }

    @Override
    public int parse(final byte[] bytes, int start, int len) {


        int offset = start;
        if (offset + 6 > len) {
            return offset;
        }
        adapter_address = new byte[6];
        System.arraycopy(bytes, offset, adapter_address, 0, 6);
        offset += 6;

        if (offset + 1 > len) {
            return offset;
        }
        version_major = BytesUtils.byteArrayToShort(bytes, offset, 1);
        offset += 1;

        if (offset + 1 > len) {
            return offset;
        }
        version_minor = BytesUtils.byteArrayToShort(bytes, offset, 1);
        offset += 1;

        if (offset + 2 > len) {
            return offset;
        }
        duration = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        frmps_received = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        frmps_transmitted = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        iframe_receive_errors = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        transmit_aborts = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 4 > len) {
            return offset;
        }
        transmitted = BytesUtils.byteArrayToLong(bytes, offset, 4);
        offset += 4;

        if (offset + 4 > len) {
            return offset;
        }
        received = BytesUtils.byteArrayToLong(bytes, offset, 4);
        offset += 4;

        if (offset + 2 > len) {
            return offset;
        }
        iframe_transmit_errors = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        no_receive_buffer = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        tl_timeouts = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        ti_timeouts = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        free_ncbs = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        ncbs = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        max_ncbs = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        no_transmit_buffers = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        max_datagram = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        pending_sessions = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        max_sessions = BytesUtils.byteArrayToInt(bytes, offset, 2);
        offset += 2;

        if (offset + 2 > len) {
            return offset;
        }
        packet_sessions = BytesUtils.byteArrayToInt(bytes, offset, 2);
        return 0;
    }

    @Override
    public int size() {

        return BTScanConstant.NBNAME_RESPONSE_FOOTER_SIZE;
    }
}
