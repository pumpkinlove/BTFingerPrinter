package com.miaxis.btfingerprinter.utils;

import java.math.BigInteger;

/**
 * Created by xu.nan on 2017/8/21.
 */

public class CodeUtil {

    public static byte[] intToByteArray(int value) {
        byte[] b = new byte[4];

        for(int i = 0; i < 4; ++i) {
            int offset = i * 8;
            b[i] = (byte)(value >> offset & 255);
        }

        return b;
    }

    public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;

        for(int i = 0; i < 4; ++i) {
            int shift = i * 8;
            value += (b[i + offset] & 255) << shift;
        }

        return value;
    }

    public static BigInteger byteArrayToBigInteger(byte[] data) {
        byte[] temp = new byte[4];

        for(int a = 0; a < 4; ++a) {
            temp[a] = data[4 - a - 1];
        }

        BigInteger var5 = new BigInteger("4294967296");
        BigInteger b = new BigInteger(temp);
        BigInteger value;
        if(BigInteger.ZERO.compareTo(b) > 0) {
            value = var5.add(b);
        } else {
            value = b;
        }

        return value;
    }

    public static byte[] shortToByteArray(short value) {
        byte[] b = new byte[2];

        for(int i = 0; i < 2; ++i) {
            int offset = i * 8;
            b[i] = (byte)(value >> offset & 255);
        }

        return b;
    }

    public static short byteArrayToShort(byte[] b, int offset) {
        short value = 0;

        for(int i = 0; i < 2; ++i) {
            int shift = i * 8;
            value = (short)(value + ((b[i + offset] & 255) << shift));
        }

        return value;
    }

    public static int JUnsigned(int x) {
        return x >= 0?x:x + 256;
    }

    public static void EncData(byte[] lpRawData, int nRawLen, byte[] lpEncData) {
        boolean i = false;

        for(int var5 = 0; var5 < nRawLen; ++var5) {
            int aaa = JUnsigned(lpRawData[var5]);
            lpEncData[2 * var5] = (byte)((aaa >> 4) + 48);
            lpEncData[2 * var5 + 1] = (byte)((aaa & 15) + 48);
        }

    }

    public static void DecData(byte[] lpEncData, int nRawLen, byte[] lpRawData) {
        boolean i = false;

        for(int var4 = 0; var4 < nRawLen; ++var4) {
            lpRawData[var4] = (byte)((lpEncData[2 * var4] - 48 << 4) + (lpEncData[2 * var4 + 1] - 48));
        }

    }

    public static String hex2str(byte[] hex) {
        StringBuilder sb = new StringBuilder();
        byte[] var5 = hex;
        int var4 = hex.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            byte b = var5[var3];
            sb.append(String.format("%02X", new Object[]{Byte.valueOf(b)}));
        }

        return sb.toString();
    }

    public static void HexToAsc(byte[] lpHexData, int nHexLength, byte[] lpAscData) {
        String strTmp = hex2str(lpHexData);
        byte[] szTmp2 = strTmp.getBytes();

        for(int i = 0; i < szTmp2.length; ++i) {
            lpAscData[i] = szTmp2[i];
        }

    }

    public static int JavaBase64Encode(byte[] pInput, int inputLen, byte[] pOutput, int outputbufsize) {
//        boolean currentin = false;
//        boolean currentin2 = false;
//        boolean currentin3 = false;
        String codebuffer = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        byte[] encodingTable = new byte[65];
        encodingTable = codebuffer.getBytes();
        int outlen = (inputLen + 2) / 3 * 4;
        int modulus = inputLen % 3;
        int datalen = inputLen - modulus;
        int encodedatalen = datalen * 4 / 3;
        if(outputbufsize < outlen) {
            return 0;
        } else {
            int i;
            int j;
            long ltmp;
            int currentin2;
            int currentin21;
            switch(modulus) {
                case 0:
                default:
                    break;
                case 1:
                    i = inputLen - 1;
                    j = outlen - 4;
                    currentin2 = pInput[i];
                    if(currentin2 < 0) {
                        currentin2 += 256;
                    }

                    ltmp = (long)currentin2 << 16;
                    pOutput[j] = encodingTable[(int)(ltmp >> 18 & 63L)];
                    pOutput[j + 1] = encodingTable[(int)(ltmp >> 12 & 63L)];
                    pOutput[j + 2] = 61;
                    pOutput[j + 3] = 61;
                    break;
                case 2:
                    i = inputLen - 2;
                    j = outlen - 4;
                    byte currentin1 = pInput[i];
                    currentin21 = pInput[i + 1];
                    if(currentin1 < 0) {
                        currentin2 = currentin1 + 256;
                    }

                    if(currentin21 < 0) {
                        currentin21 += 256;
                    }

                    ltmp = (long)pInput[i] << 16 | (long)currentin21 << 8;
                    pOutput[j] = encodingTable[(int)(ltmp >> 18 & 63L)];
                    pOutput[j + 1] = encodingTable[(int)(ltmp >> 12 & 63L)];
                    pOutput[j + 2] = encodingTable[(int)(ltmp >> 6 & 63L)];
                    pOutput[j + 3] = 61;
            }

            i = datalen - 3;

            for(j = encodedatalen - 4; i >= 0; j -= 4) {
                currentin2 = pInput[i];
                currentin21 = pInput[i + 1];
                int currentin31 = pInput[i + 2];
                if(currentin2 < 0) {
                    currentin2 += 256;
                }

                if(currentin21 < 0) {
                    currentin21 += 256;
                }

                if(currentin31 < 0) {
                    currentin31 += 256;
                }

                ltmp = (long)currentin2 << 16 | (long)currentin21 << 8 | (long)currentin31;
                pOutput[j] = encodingTable[(int)(ltmp >> 18 & 63L)];
                pOutput[j + 1] = encodingTable[(int)(ltmp >> 12 & 63L)];
                pOutput[j + 2] = encodingTable[(int)(ltmp >> 6 & 63L)];
                pOutput[j + 3] = encodingTable[(int)(ltmp & 63L)];
                i -= 3;
            }

            return outlen;
        }
    }

    public static int JavaBase64Decode(byte[] pInput, int inputLen, byte[] pOutput) {
        short np = 255;
        char[] decodingTable = new char[256];

        int i;
        for(i = 0; i < 256; ++i) {
            decodingTable[i] = (char)np;
        }

        for(i = 65; i <= 90; ++i) {
            decodingTable[i] = (char)(i - 65);
        }

        for(i = 97; i <= 122; ++i) {
            decodingTable[i] = (char)(i - 97 + 26);
        }

        for(i = 48; i <= 57; ++i) {
            decodingTable[i] = (char)(i - 48 + 52);
        }

        decodingTable[43] = 62;
        decodingTable[47] = 63;
        if(inputLen % 4 != 0) {
            return 0;
        } else {
            byte padnum;
            if(pInput[inputLen - 2] == 61) {
                padnum = 2;
            } else if(pInput[inputLen - 1] == 61) {
                padnum = 1;
            } else {
                padnum = 0;
            }

            int outlen = inputLen / 4 * 3 - padnum;
            int datalen = (inputLen - padnum) / 4 * 3;
            i = 0;

            int m;
            long ltmp;
            char ctmp;
            for(int j = 0; i < datalen; j += 4) {
                ltmp = 0L;

                for(m = j; m < j + 4; ++m) {
                    ctmp = decodingTable[pInput[m]];
                    if(ctmp == np) {
                        return 0;
                    }

                    ltmp = ltmp << 6 | (long)ctmp;
                }

                pOutput[i] = (byte)((int)(ltmp >> 16 & 255L));
                pOutput[i + 1] = (byte)((int)(ltmp >> 8 & 255L));
                pOutput[i + 2] = (byte)((int)(ltmp & 255L));
                i += 3;
            }

            switch(padnum) {
                case 0:
                default:
                    break;
                case 1:
                    ltmp = 0L;

                    for(m = inputLen - 4; m < inputLen - 1; ++m) {
                        ctmp = decodingTable[pInput[m]];
                        if(ctmp == np) {
                            return 0;
                        }

                        ltmp = ltmp << 6 | (long)ctmp;
                    }

                    ltmp <<= 6;
                    pOutput[outlen - 2] = (byte)((int)(ltmp >> 16 & 255L));
                    pOutput[outlen - 1] = (byte)((int)(ltmp >> 8 & 255L));
                    break;
                case 2:
                    ltmp = 0L;

                    for(m = inputLen - 4; m < inputLen - 2; ++m) {
                        ctmp = decodingTable[pInput[m]];
                        if(ctmp == np) {
                            return 0;
                        }

                        ltmp = ltmp << 6 | (long)ctmp;
                    }

                    ltmp <<= 12;
                    pOutput[outlen - 1] = (byte)((int)(ltmp >> 16 & 255L));
            }

            return outlen;
        }
    }

    public static byte getXorCheckCode(byte[] data) {
        byte temp = data[1];
        for (int i = 2; i < data.length - 2; i++) {
            temp ^= data[i];
        }
        return temp;
    }

    public static byte[] getData(byte[] mergeData) {
        byte[] temp = new byte[mergeData.length - 7];
        System.arraycopy(mergeData, 5, temp, 0, temp.length);
        return temp;
    }

    private static byte[] splitByte(byte bByte) {
        short tempShort = (short)bByte;
        if (tempShort < 0) {
            tempShort += 256;
        }
        short high4 = (short) (tempShort & 0xf0);
        short low4  = (short) (tempShort & 0x0f);

        byte[] reBytes = new byte[2];
        reBytes[0] = (byte) ((high4 >> 4) + 0x30);
        reBytes[1] = (byte) (low4 + 0x30);
        return reBytes;
    }

    private static byte mergeBytes(byte bByte0, byte bByte1) {
        return (byte) (((bByte0 - 0x30) << 4) + (bByte1 - 0x30));
    }

    public static byte[] splitReqBytes(byte[] orgBytes) {
        byte[] splitBytes = new byte[2 * orgBytes.length - 2];
        splitBytes[0] = orgBytes[0];
        splitBytes[splitBytes.length - 1] = orgBytes[orgBytes.length - 1];
        for (int i = 1; i < orgBytes.length - 1; i++) {
            byte[] temp = splitByte(orgBytes[i]);
            splitBytes[i * 2 - 1] = temp[0];
            splitBytes[i * 2] = temp[1];
        }
        return splitBytes;
    }

    public static byte[] mergeRetBytes(byte[] orgBytes) {
        byte[] mergeBytes = new byte[orgBytes.length / 2 + 1];
        mergeBytes[0] = orgBytes[0];
        mergeBytes[mergeBytes.length - 1] = orgBytes[orgBytes.length - 1];
        for (int i = 1; i < mergeBytes.length - 1; i++) {
            byte temp = mergeBytes(orgBytes[2 * i - 1], orgBytes[2 * i]);
            mergeBytes[i] = temp;
        }
        return mergeBytes;
    }

}
