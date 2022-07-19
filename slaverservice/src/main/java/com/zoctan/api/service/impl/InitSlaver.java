package com.zoctan.api.service.impl;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.zoctan.api.entity.Slaver;
import com.zoctan.api.mapper.SlaverMapper;
import com.zoctan.api.util.IPHelpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by fanseasn on 2020/11/26.
 */
/*
 @author Season
 @DESCRIPTION 
 @create 2020/11/26
*/
@Component
@Slf4j
public class InitSlaver {

    @Value("${server.port}")
    private String port;

    @Autowired
    private SlaverMapper slaverMapper;

    @PostConstruct
    public void Init() {
        InitSlaver.log.info("启动开始注册测试执行机。。。。。。。。。。。。。。");
        //InetAddress address = null;
        try {
            //address = InetAddress.getLocalHost();
            String ip = IPHelpUtils.getInet4Address();//address.getHostAddress();
            InitSlaver.log.info("启动注册slaver获取ip地址......................................................." + ip);
            String MacAddredss = "";
            try {
                //MacAddredss = getMacByIP(ip);
                List<String>list= getMACAddress();
                MacAddredss=list.get(0);
            } catch (Exception ex) {
                InitSlaver.log.info("启动注册slaver获取mac地址失败......................................................." + ex.getMessage());
            }
            Slaver sa = new Slaver();
            sa.setMacaddredss(MacAddredss);
            sa.setIp(ip);
            sa.setSlavername("执行机" + ip);
            sa.setStype("功能");
            sa.setPort(port);
            sa.setStatus("空闲");
            sa.setMemo("执行机" + ip);

            if(ip!=null)
            {
                List<Slaver> slaverList= slaverMapper.findslaverbyip(ip);
                if(slaverList.size()>0)
                {
                    Slaver slaver=slaverList.get(0);
                    if (slaver.getStatus().equals("已下线")||slaver.getStatus().equals("运行中")) {
                        slaver.setStatus("空闲");
                        slaverMapper.updateSlaver(slaver);
                        InitSlaver.log.info("slaver的mac和IP相同，如果是下线状态则更新为上线空闲.......................................................");
                    }
                }
                else
                {
                    slaverMapper.addslaver(sa);
                }
            }
//            List<Slaver> slaverList = slaverMapper.findslaverbymac(MacAddredss);
//            if (slaverList.size() == 0) {
//                slaverMapper.addslaver(sa);
//                InitSlaver.log.info("启动注册slaver完成.......................................................");
//            } else {
//                Slaver slaver = slaverList.get(0);
//                if(!slaver.getIp().equalsIgnoreCase(ip))
//                {
//                    //如果已存在此mac地址，并且ip不一样，更新为新的IP，服务器可能改过host的ip
//                    slaverMapper.updateSlaver(slaver);
//                    InitSlaver.log.info("slaver已存在此mac地址，并且ip不一样，更新为新的IP，服务器可能改过host的ip.......................................................");
//                }
//                else
//                {
//                    //如果IP也相同，如果是下线状态则更新为上线空闲
//                    if (slaver.getStatus().equals("已下线")) {
//                        slaver.setStatus("空闲");
//                        slaverMapper.updateSlaver(slaver);
//                        InitSlaver.log.info("slaver的mac和IP相同，如果是下线状态则更新为上线空闲.......................................................");
//                    }
//                }
//                InitSlaver.log.info("slaver已注册.......................................................");
//            }
        } catch (Exception e) {
            InitSlaver.log.info("启动注册测试执行机异常，请检查当前服务器host文件是否正常配置本机IP：" + e.getMessage());
        }

    }

    private String getMacByIP(String IP) throws Exception {
        InetAddress ia = InetAddress.getByName(IP);
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            String hexString = Integer.toHexString(mac[i] & 0xFF);
            sb.append(hexString.length() == 1 ? "0" + hexString : hexString);
        }
        return sb.toString().toUpperCase();
    }


    public static List<String> getMACAddress() throws SocketException {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        List<String> list = new ArrayList<>();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            if (ni != null && ni.isUp()) {
                byte[] bytes = ni.getHardwareAddress();
                if (bytes != null && bytes.length == 6) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : bytes) {
                        //与11110000作按位与运算以便读取当前字节高4位
                        sb.append(Integer.toHexString((b & 240) >> 4));
                        //与00001111作按位与运算以便读取当前字节低4位
                        sb.append(Integer.toHexString(b & 15));
                        sb.append("-");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    list.add(sb.toString().toUpperCase());
                }
            }
        }
        return list;
    }


//    public static String getInet4Address() {
//        Enumeration<NetworkInterface> nis;
//        String ip = null;
//        try {
//            nis = NetworkInterface.getNetworkInterfaces();
//            for (; nis.hasMoreElements();) {
//                NetworkInterface ni = nis.nextElement();
//                Enumeration<InetAddress> ias = ni.getInetAddresses();
//                for (; ias.hasMoreElements();) {
//                    InetAddress ia = ias.nextElement();
//                    //ia instanceof Inet6Address && !ia.equals("")
//                    if (ia instanceof Inet4Address && !ia.getHostAddress().equals("127.0.0.1")) {
//                        ip = ia.getHostAddress();
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            InitSlaver.log.info("slaver-getInet4Address......................................................."+e.getMessage());
//        }
//        return ip;
//    }


    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("unix/linux方式未获取到网卡地址");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }


    public static void main(String[] args) {

        try {
            //List<String>list= getMACAddress();
//            String macAddress= getUnixMACAddress();
//            String ip= getInet4Address();
//            System.out.println(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
