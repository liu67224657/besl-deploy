/**
 * (c) 2008 Platform Team of ccnec.com
 */
package com.fivewh.deploy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: <a mailto:yinpy@ccnec.com>Yin Pengyi</a>
 */
public class MD5Encoder {
    private final static String[] hexDigits = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static MessageDigest mdInstance = null;

    public MD5Encoder() {
        try {
            mdInstance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            //
        }
    }

    public String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }

        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    public String encode(final String origin) {
        String resultString = null;

        resultString = new String(origin);
        resultString = byteArrayToHexString(mdInstance.digest(resultString.getBytes()));

        return resultString;
    }

    public static void main(String[] args) {
        MD5Encoder md5Encoder = new MD5Encoder();

        System.out.print(md5Encoder.encode(args[0]));
    }
}
