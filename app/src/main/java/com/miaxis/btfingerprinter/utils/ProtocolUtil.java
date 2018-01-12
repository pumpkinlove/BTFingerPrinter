package com.miaxis.btfingerprinter.utils;

/**
 * Created by xu.nan on 2017/8/17.
 */

public class ProtocolUtil {

    public static String hex2String(String hex) throws Exception{
        String r = bytes2String(hexString2Bytes(hex));
        return r;
    }

    public static String bytes2String(byte[] b) throws Exception {
        String r = new String (b,"UTF-8");
        return r;
    }

    public static byte[] hexString2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))){
            return null;
        }
        else if (hex.length()%2 != 0){
            return null;
        }
        else{
            hex = hex.toUpperCase();
            int len = hex.length()/2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i=0; i<len; i++){
                int p=2*i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
            }
            return b;
        }

    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


}
