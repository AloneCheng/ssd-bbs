package com.ssdkj.bbs.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class ZipUtils {
    // 解压
    public static String decompressData(String encdata) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InflaterOutputStream zos = new InflaterOutputStream(bos);
            zos.write(convertFromBase64(encdata));
            zos.close();
            return new String(bos.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // 压缩
    private static byte[] convertFromBase64(String encdata) {
        byte[] compressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(encdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressed;
    }
    public static String compressData(String data) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DeflaterOutputStream zos = new DeflaterOutputStream(bos);
            zos.write(data.getBytes());
            zos.close();
            return new String(convertToBase64(bos.toByteArray()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static String convertToBase64(byte[] byteArray) {
        return new sun.misc.BASE64Encoder().encode(byteArray);
    }


    public static void main(String[] args) {
        String articleImgURL = "http://pzcximdnk.bkt.clouddn.com/FsOrBQYNoMefSKJX2WcIBKw5zBK2|http://pzcximdnk.bkt.clouddn.com/FsOrBQYNoMefSKJX2WcIBKw5zBK2|http://pzcximdnk.bkt.clouddn.com/FrsAud8VcRerBf8BXSO151v8CeK5|http://pzcximdnk.bkt.clouddn.com/FrsAud8VcRerBf8BXSO151v8CeK5|http://pzcximdnk.bkt.clouddn.com/FrsAud8VcRerBf8BXSO151v8CeK5|http://pzcximdnk.bkt.clouddn.com/FrsAud8VcRerBf8BXSO151v8CeK5\",\"articleTag\":4,\"articleType\":1,\"headImgurl\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/CaaCsYE8TTdMeRDiaksyzysslibswv1GcBFAkfs8Ajv61CbricarKaRWOE2ia1SpSolHlqeXXBpGTldRMX6BUj600A/132";
        String convert = compressData(articleImgURL);

        System.out.println(articleImgURL.length());
        System.out.println(convert);
        System.out.println(convert.length());
    }
}
