package com.zoctan.api.util;

import com.zoctan.api.service.impl.InitSlaver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Enumeration;

/**
 * AES加解密工具
 *
 * @author Zoctan
 * @date 2018/11/29
 */
@Slf4j
public class IPHelpUtils {
  public static String getInet4Address() {
    Enumeration<NetworkInterface> nis;
    String ip = null;
    try {
      nis = NetworkInterface.getNetworkInterfaces();
      for (; nis.hasMoreElements();) {
        NetworkInterface ni = nis.nextElement();
        Enumeration<InetAddress> ias = ni.getInetAddresses();
        for (; ias.hasMoreElements();) {
          InetAddress ia = ias.nextElement();
          //ia instanceof Inet6Address && !ia.equals("")
          if (ia instanceof Inet4Address && !ia.getHostAddress().equals("127.0.0.1")) {
            ip = ia.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      IPHelpUtils.log.info("slaver-getInet4Address......................................................."+e.getMessage());
    }
    return ip;
  }
}
