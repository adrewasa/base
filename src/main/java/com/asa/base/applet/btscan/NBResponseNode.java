package com.asa.base.applet.btscan;

/**
 * Created by andrew_asa on 2017/7/27.
 */
public class NBResponseNode {

    NetBiosResponse response;

    public NBResponseNode(NetBiosResponse response) {

        this.response = response;
    }

    public NetBiosResponse getResponse() {

        return response;
    }
}
