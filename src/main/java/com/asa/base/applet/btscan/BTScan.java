package com.asa.base.applet.btscan;


import com.asa.base.io.IOUtils;
import com.asa.base.utils.data.GeneralUtils;
import com.asa.base.utils.net.IPUtils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by andrew_asa on 2017/7/19.
 */
public class BTScan {


    List<NBResponseNode> nodes = new ArrayList<NBResponseNode>();

    Socket socket;

    /**
     * 同样支持命令行的形式进行调用
     *
     * @param args
     */
    public BTScan(String args[]) {

        Options options = getArgsOptions();
        DefaultParser parser = new DefaultParser();
        try {
            CommandLine cl = parser.parse(options, args);
            if (cl.hasOption("h")) {
                HelpFormatter f = new HelpFormatter();
                f.printHelp("使用说明", options);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Options getArgsOptions() {

        Options options = new Options();
        options.addOption("server", false, "启动服务器");
        options.addOption("client", true, "发送请求 client=cmd");
        options.addOption("h", "帮助文档");
        return options;
    }

    /**
     * 获取ips对应的地址的节点信息
     *
     * @param ips
     * @return
     */
    public List<NBResponseNode> getNodes(List<String> ips, boolean printRequestDetail) {

        List<NBResponseNode> ret = new ArrayList<NBResponseNode>();
        if (GeneralUtils.containNull(ips)) {
            return ret;
        }
        Selector selector = null;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
            return ret;
        }
        if (GeneralUtils.allNotNull(selector)) {
            NetBiosRequest request = new NetBiosRequest(true);
            byte[] rbs = request.tobytes();
            int l = Math.min(BTScanConstant.SELECT_WATCH_LEN, ips.size());
            // 进行初始化n个
            for (int i = 0; i < l; i++) {
                try {
                    DatagramChannel channel = addChannel(ips, selector, new Integer(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 是否所有的都已经扫描完成
            boolean allFinish = false;
            while (true && !allFinish) {
                try {
                    int is = selector.select(BTScanConstant.TIME_OUT);
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                    if (selectionKeySet.size() > 0) {
                        int removeCount = 0;
                        while (iterator.hasNext()) {
                            // 是否剔除
                            SelectionKey key = iterator.next();
                            DatagramChannel channel = (DatagramChannel) key.channel();
                            DatagramSocket socket = channel.socket();
                            Integer tr = (Integer) key.attachment();
                            if (key.isReadable()) {
                                // 端口可读
                                try {
                                    ByteBuffer rev = ByteBuffer.allocate(BTScanConstant.BUF_DATA_LEN);
                                    rev.clear();
                                    int revL = 0;
                                    // 获取返回数据
                                    while (true) {
                                        int tl;
                                        try {
                                            tl = channel.read(rev);
                                            if (tl == -1) {
                                                break;
                                            }
                                            if (tl <= 0) {
                                                break;
                                            }
                                            revL += tl;
                                        } catch (IOException e) {
                                            //e.printStackTrace();
                                            revL = 0;
                                            break;
                                        }
                                    }
                                    // 有数据返回
                                    if (revL > 0) {
                                        byte[] revBuf = new byte[BTScanConstant.BUF_DATA_LEN];
                                        rev.position(0);
                                        rev.get(revBuf, 0, revL);
                                        NetBiosResponse response = new NetBiosResponse(revBuf, revL, socket.getInetAddress());
                                        NBResponseNode retn = new NBResponseNode(response);
                                        ret.add(retn);
                                        if (printRequestDetail) {
                                            response.print();
                                        }
                                    }
                                } catch (Exception e) {

                                } finally {
                                    // 不管有没有解析成功都进行删除
                                    removeCount++;
                                    iterator.remove();
                                    IOUtils.closeQuietly(channel);
                                }
                            } else if (key.isWritable()) {
                                // 可以写,往里面发送请求
                                if (tr.intValue() > BTScanConstant.TRIES_TIME) {
                                    removeCount++;
                                    iterator.remove();
                                    IOUtils.closeQuietly(channel);
                                    // 删掉
                                    if (printRequestDetail) {
                                        // 这边不能再这里进行getHostName,window下该方法慢死------
                                        //System.out.println(socket.getInetAddress().getHostName() + " try time out");
                                    }
                                } else {
                                    writerRequestChannel(rbs, channel);
                                    key.attach(new Integer(tr.intValue() + 1));
                                }
                            }
                        }
                        // 如果需要剔除||
                        if (removeCount > 0) {
                            for (int i = 0; i < removeCount; i++) {
                                try {
                                    DatagramChannel c = addChannel(ips, selector, new Integer(0));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        allFinish = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    // 必须强制休息,要不然没有解析完请求就已经跑完了....
                    // TODO 这种方法显然是很不好的 仍然需要进一步的完善
                    Thread.sleep(BTScanConstant.TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;

    }

    private void writerRequestChannel(byte[] rbs, DatagramChannel channel) {

        ByteBuffer buf = ByteBuffer.allocate(rbs.length);
        buf.clear();
        buf.put(rbs);
        buf.flip();
        int l = rbs.length;
        try {
            while (l > 0) {
                int t = channel.write(buf);
                l -= t;
            }
        } catch (Exception e) {

        }
    }

    private DatagramChannel addChannel(List<String> ips, Selector selector, Integer time) throws Exception {

        if (ips.size() > 0) {
            InetAddress ip = InetAddress.getByName(ips.remove(ips.size() - 1));
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(ip, BTScanConstant.NET_BIOS_PORT);
            channel.socket().setSoTimeout(BTScanConstant.TIME_OUT);
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, time);
            channel.socket().connect(address);
            // 非阻塞的
            return channel;
        }
        return null;
    }


    @Deprecated
    /**
     * 阻塞性进行获取
     */
    public NBResponseNode getNode(InetAddress dstIp, boolean printReqDeteail) {

        if (dstIp == null) {
            return null;
        }
        String dsName = dstIp.getHostName();
        NBResponseNode ret = null;
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            NetBiosRequest request = new NetBiosRequest(true);
            byte[] rp = request.tobytes();
            DatagramPacket packet = new DatagramPacket(rp, rp.length, dstIp, BTScanConstant.NET_BIOS_PORT);
            // 设置超时
            ds.setSoTimeout(BTScanConstant.TIME_OUT);
            byte[] inBuff = new byte[BTScanConstant.BUF_DATA_LEN];
            DatagramPacket dp_receive = new DatagramPacket(inBuff, BTScanConstant.BUF_DATA_LEN);
            int tries = 0;                         //重发数据的次数
            boolean receivedResponse = false;     //是否接收到数据的标志位
            while (!receivedResponse && tries < BTScanConstant.TRIES_TIME) {
                //发送数据
                ds.send(packet);
                try {
                    //接收从服务端发送回来的数据
                    ds.receive(dp_receive);
                    //如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
                    receivedResponse = true;
                } catch (InterruptedIOException e) {
                    //如果接收数据时阻塞超时，重发并减少一次重发的次数
                    tries += 1;
                    if (printReqDeteail) {
                        System.out.println("Time out," + (BTScanConstant.TRIES_TIME - tries) + " more tries...");
                    }
                }
            }
            if (receivedResponse) {
                //如果收到数据，则打印出来
                if (printReqDeteail) {
                    System.out.println("client received data from server：" + dsName);
                }
                byte[] rbytes = dp_receive.getData();
                int l = dp_receive.getLength();
                NetBiosResponse response = new NetBiosResponse(rbytes, l, dstIp);
                ret = new NBResponseNode(response);
            } else {
                //如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
                if (printReqDeteail) {
                    System.out.println("[" + dsName + "]No response -- give up.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ds);
        }
        return ret;
    }

    public void printfS() {

        System.out.println(getIntroduceStr());
        System.out.println("------------------------------------------------------------------------------");
    }

    public static String getIntroduceStr() {

        return String.format("%-17s%-17s%-10s%-17s%-17s", "IP address", "NetBIOS Name",
                "Server", "User", "MAC address");
    }


    public static void main(String[] args) {

        BTScan btScan = new BTScan(args);
        try {
            //InetAddress dstIp = InetAddress.getByName("192.168.1.101");
            //NBResponseNode n = btScan.getNode(dstIp, true);
            //if (GeneralUtils.allNotNull(n)) {
            //    n.getResponse().print();
            //}
            List<String> ips = IPUtils.getLocalIpSegment(1, 255);
            List<NBResponseNode> ns = btScan.getNodes(ips, true);
            btScan.printfS();
            for (NBResponseNode n : ns) {
                n.getResponse().print();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
