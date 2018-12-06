package com.berry.fep.commons.utils;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.*;
import java.util.Arrays;

public class ConvertTools {

    /**
     * Mask for bit 0 of a byte.
     */
    private static final int BIT_0 = 1;

    /**
     * Mask for bit 1 of a byte.
     */
    private static final int BIT_1 = 0x02;

    /**
     * Mask for bit 2 of a byte.
     */
    private static final int BIT_2 = 0x04;

    /**
     * Mask for bit 3 of a byte.
     */
    private static final int BIT_3 = 0x08;

    /**
     * Mask for bit 4 of a byte.
     */
    private static final int BIT_4 = 0x10;

    /**
     * Mask for bit 5 of a byte.
     */
    private static final int BIT_5 = 0x20;

    /**
     * Mask for bit 6 of a byte.
     */
    private static final int BIT_6 = 0x40;

    /**
     * Mask for bit 7 of a byte.
     */
    private static final int BIT_7 = 0x80;

    private static final int[] BITS = {BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7};

    /**
     * 把16进制字符串转换成字节数组byte[],从左向右，两位一算，若不是2的倍数，前面补0
     * 其中16进制输入，可以为小写，将自动转换为大写并操作
     *
     * @param hexStr 16进制字符串
     * @return 字节数组
     */
    public static byte[] hexStringToByte(String hexStr) {
        hexStr = hexStr.length() % 2 == 1 ? "0" + hexStr : hexStr;
        int len = (hexStr.length() / 2);
        byte[] result = new byte[len];
        char[] chars = hexStr.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            //两位一算，pos位对应值左移4位 与 pos+1位对应值进行与运算，得出对应16进制两位字符的10进制值
            result[i] = (byte) (charToByte(chars[pos]) << 4 | charToByte(chars[pos + 1]));
        }
        return result;
    }

    /**
     * 单个字符转整型
     *
     * @param b
     * @return
     */
    public static int getIntByByte(byte b) {
        return (b & 0xff);
    }

    /**
     * 16进制对应单个字符对应的10进制值，以byte形式返回
     *
     * @param c
     * @return
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 把字节数组转换为对象
     *
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(in);
        Object o = oi.readObject();
        oi.close();
        return o;
    }

    /**
     * 把可序列化对象转换成字节数组
     *
     * @param s
     * @return
     * @throws IOException
     */
    public static byte[] objectToBytes(Serializable s) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream ot = new ObjectOutputStream(out);
        ot.writeObject(s);
        ot.flush();
        ot.close();
        return out.toByteArray();
    }


    /**
     * BCD码转为10进制串
     *
     * @param bytes
     * @return
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    /**
     * 10进制串转为BCD码
     *
     * @param ascii
     * @return
     */
    public static byte[] str2Bcd(String ascii) {
        String asc = ascii;
        int len = asc.length();
        int mod = len % 2;

        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }

        byte abt[] = null;
        if (len >= 2) {
            len = len / 2;
        }

        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;

        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }

            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    /**
     * 字节数组转整型
     *
     * @param resource
     * @return
     */
    public static int bytesToInt(byte[] resource) {
        if (resource == null) {
            return 0;
        }
        int len = resource.length;
        int result = 0;
        for (int i = 0; i < len; i++) {
            result += (resource[i] & 0xff) << (8 * (len - i - 1));
        }
        return result;
    }


    /**
     * 整型转字节数组
     *
     * @param num
     * @return
     */
    public static byte[] int2bytes(int num) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (((long) num >>> (24 - i * 8)) & 0xFF);
        }
        return b;
    }

    /**
     * 把字节数组转换成ASCII '0''1'表示字符串,不使用apache的codec的原因是它的bincodec是反过来的
     *
     * @param raw
     * @return
     */
    public static char[] bytesToascii(byte[] raw) {
        if (raw == null || raw.length == 0) {
            return new char[0];
        }
        // get 8 times the bytes with 3 bit shifts to the left of the length
        char[] lascii = new char[raw.length << 3];
        /*
         * We decr index jj by 8 as we go along to not recompute indices using
         * multiplication every time inside the loop.
         */
        for (int ii = raw.length - 1, jj = lascii.length - 1; ii >= 0; ii--, jj -= 8) {
            for (int bits = 0; bits < BITS.length; ++bits) {
                if ((raw[ii] & BITS[bits]) == 0) {
                    lascii[jj - bits] = '0';
                } else {
                    lascii[jj - bits] = '1';
                }
            }
        }
        return lascii;
    }

    /**
     * 将字符数组转换为字节数组
     *
     * @param chars 字符数组 0111111111000000000000000000000000000000000000000000000000000000
     *              对应的2进制 01111111 11000000 00000000 00000000 00000000 00000000 00000000 00000000
     * @return bytes 字节数组 [127      -64        0       0         0        0        0        0]
     */
    public static byte[] charsToBytes(char[] chars) {
        if (chars == null || chars.length == 0) {
            return new byte[0];
        }
        // get length/8 times bytes with 3 bit shifts to the right of the length
        byte[] lraw = new byte[chars.length >> 3];
        /*
         * We decr index jj by 8 as we go along to not recompute indices using
         * multiplication every time inside the loop.
         */
        for (int ii = lraw.length - 1, jj = chars.length - 1; ii >= 0; ii--, jj -= 8) {
            for (int bits = 0; bits < BITS.length; ++bits) {
                if (chars[jj - bits] == '1') {
                    lraw[ii] |= BITS[bits];
                }
            }
        }
        return lraw;
    }

    /**
     * 将2进制字符串转换为16进制字符串
     *
     * @param binaryStr 2进制字符串
     * @return
     */
    public static String convertBinaryToHexString(String binaryStr) {
        return ConvertTools.bytesToHexString(ConvertTools.charsToBytes(binaryStr.toCharArray()));
    }

    /**
     * 16进制字符串转10进制
     *
     * @param hex
     * @return
     */

    public static int hexStringToOctal(String hex) {

        int result = 0;

        for (char c : hex.toCharArray()) {
            result = (result << 4) + Character.digit(c, 16);
        }

        return result;
    }

    /**
     * 16进制转换成ASCII码
     *
     * @param hex 16进制字符串
     * @return ASCII码
     */
    public static String convertHexToASCIIString(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
        }
        return sb.toString();
    }
}
