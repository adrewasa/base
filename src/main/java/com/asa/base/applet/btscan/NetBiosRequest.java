package com.asa.base.applet.btscan;


import com.asa.base.utils.data.bytes.BytesBuffer;
import com.asa.base.utils.data.bytes.BytesUtils;

/**
 * Created by andrew_asa on 2017/7/19.
 */
public class NetBiosRequest {

    private int transaction_id;

    private int flags;

    private int question_count;

    private int answer_count;

    private int name_service_count;

    private int additional_record_count;

    private char[] question_name;

    private byte[] name;

    private int question_type;

    private int question_class;

    /**
     * 是否已经进行初始化
     */
    private boolean isInit = false;

    /**
     * 请求总字节数
     */
    private int TOTALLEN = 50;

    public NetBiosRequest(boolean isInit) {

        if (isInit) {
            this.isInit = true;
            question_name = new char[34];
            name = new byte[34];
            flags = BTScanConstant.FL_BROADCAST;
            question_count = 1;
            answer_count = 0;
            name_service_count = 0;
            additional_record_count = 0;
            question_type = 0x21;
            question_class = 0x01;
            //question_name[0] = BytesUtils.byteArrayToChar(new byte[]{0x20, 0x43});
            //question_name[33] = BytesUtils.byteArrayToChar(new byte[]{0x41, 0x00});
            // TODO
            name[0] = 0x20;
            name[1] = 0x43;
            name[2] = 0x4b;
            name[3] = 0x41;
            name[32] = 0x41;
            name[33] = 0x00;
            for (int i = 4; i < 33; i++) {
                name[i] = 0x41;
            }
            //transaction_id = 44;
        }
    }

    /**
     * 获取字节
     * 需要变成 uint16_t的类型
     *
     * @return
     */
    public byte[] tobytes() {

        if (isInit) {
            BytesBuffer bf = new BytesBuffer();
            //bf.append(BytesUtils.toByteArray(transaction_id, 2));
            bf.append(new byte[]{
                    0x03, 0x33
            });
            bf.append(BytesUtils.toByteArray(flags, 2));
            bf.append(BytesUtils.toByteArray(question_count, 2));
            bf.append(BytesUtils.toByteArray(answer_count, 2));
            bf.append(BytesUtils.toByteArray(name_service_count, 2));
            bf.append(BytesUtils.toByteArray(additional_record_count, 2));
            bf.append(name);
            //for (int i = 0; i < question_name.length; i++) {
            //    bf.append(BytesUtils.toByteArray(question_name[i], 1));
            //}
            bf.append(BytesUtils.toByteArray(question_type, 2));
            bf.append(BytesUtils.toByteArray(question_class, 2));
            byte[] ret = new byte[bf.length()];
            bf.getBytes(0, bf.length(), ret, 0);
            return ret;
        } else {
            return new byte[0];
        }
    }

    public void setInit(boolean init) {

        this.isInit = init;
    }

}
