package tseng.min.c.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by Neo on 2015/11/11.
 */
public class Convert {
    private static final String TAG = Convert.class.getSimpleName();

    public static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }

    public static int b2Int(byte paramByte) {
        return (paramByte + 256) % 256;
    }

    public static int byteArray2Int(byte[] paramArrayOfByte) {
        return byteArray2Int(paramArrayOfByte, 0);
    }

    public static int byteArray2Int(byte[] paramArrayOfByte, int paramInt) {
        return (b2Int(paramArrayOfByte[(paramInt + 3)]) << 24)
                + (b2Int(paramArrayOfByte[(paramInt + 2)]) << 16)
                + (b2Int(paramArrayOfByte[(paramInt + 1)]) << 8)
                + b2Int(paramArrayOfByte[paramInt]);
    }

    public static short byteArray2Short(byte[] paramArrayOfByte, int paramInt) {
        return (short) ((b2Int(paramArrayOfByte[(paramInt + 1)]) << 8) + b2Int(paramArrayOfByte[paramInt]));
    }

    public static String byteArray2String(byte[] paramArrayOfByte) {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < paramArrayOfByte.length; i++) {
            if (paramArrayOfByte[i] == 0x0) {
                break;
            }
            sBuilder.append((char) paramArrayOfByte[i]);
        }
        return sBuilder.toString();
//        return byteArray2String(paramArrayOfByte, " ");
    }

    public static String byteArray2String(byte[] paramArrayOfByte, String paramString) {
        if (paramArrayOfByte == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        int i = paramArrayOfByte.length;
        for (int j = 0; ; j++) {
            if (j >= i) {
                return localStringBuilder.toString();
            }
            byte b = paramArrayOfByte[j];
            String str = "%02x" + paramString;
            localStringBuilder.append(String.format(str, b));
        }
    }

    public static String getDigest(String paramString) {
        try {
            String str = byteArray2String(MessageDigest.getInstance("MD5")
                    .digest(paramString.getBytes("UTF-8")), "");
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public static String getRandomHexString(int paramInt) {
        Random localRandom = new Random(System.currentTimeMillis());
        byte[] arrayOfByte = new byte[paramInt];
        localRandom.nextBytes(arrayOfByte);
        return byteArray2String(arrayOfByte, "");
    }

    public static byte[] int2ByteArray(int paramInt) {
        byte[] arrayOfByte = new byte[4];
        arrayOfByte[0] = ((byte) paramInt);
        arrayOfByte[1] = ((byte) (paramInt >> 8));
        arrayOfByte[2] = ((byte) (paramInt >> 16));
        arrayOfByte[3] = ((byte) (paramInt >> 24));
        return arrayOfByte;
    }

    public static class WiFiCmdResponse {
        public static int channel;  // AV Server Index
        public static int nCmdIdentify;  // (Option) a number let app can distinguish the response;  0 : disable
        public static int nAVChannel4LongReq;     // (Option) if large sent data transfer required when request;  0 : disable
        public static int nCmdID;      // The same as Command ID value defined in param's cmd.
        public static int nTotalCount;    // The total amount of io command transfer times for this wifi command.
        public static int nIndex;     // The index of io command transfer times for this wifi command
        public static int nDataLength;    // The valid data length in bytes of the param
        public static byte[] response = new byte[972];    // The response  string

        public static void fillContent(byte[] buf) {
            try {
                channel = byteArrayToInt_Little(buf, 0);
                nCmdIdentify = byteArrayToInt_Little(buf, 4);
                nAVChannel4LongReq = byteArrayToInt_Little(buf, 8);
                nCmdID = byteArrayToInt_Little(buf, 12);
                nTotalCount = byteArrayToInt_Little(buf, 16);
                nIndex = byteArrayToInt_Little(buf, 20);
                nDataLength = byteArrayToInt_Little(buf, 24);
                System.arraycopy(buf, 28, response, 0, nDataLength);
            } catch (Exception e) {
                Log.d(TAG, "Show Exception : " + e);
            }
        }
    }

    // http://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java
    public static final int byteArrayToInt_Little(byte byt[], int nBeginPos) {
        return (0xff & byt[nBeginPos]) | (0xff & byt[nBeginPos + 1]) << 8 | (0xff & byt[nBeginPos + 2]) << 16 | (0xff & byt[nBeginPos + 3]) << 24;
    }

    public static byte[] sendWiFiCmdRequest(int channel, int nCmdIdentify, int nAVChannel4LongReq, int nCmdID, int nTotalCount, int nIndex, int nDataLength, String param) {
        byte[] result = new byte[1000];
        byte[] ch = intToByteArray_Little(channel);
        byte[] cmdId = intToByteArray_Little(nCmdIdentify);
        byte[] longReq = intToByteArray_Little(nAVChannel4LongReq);
        byte[] cmdID = intToByteArray_Little(nCmdID);
        byte[] count = intToByteArray_Little(nTotalCount);
        byte[] index = intToByteArray_Little(nIndex);
        byte[] length = intToByteArray_Little(nDataLength);

        System.arraycopy(ch, 0, result, 0, 4);
        System.arraycopy(cmdId, 0, result, 4, 4);
        System.arraycopy(longReq, 0, result, 8, 4);
        System.arraycopy(cmdID, 0, result, 12, 4);
        System.arraycopy(count, 0, result, 16, 4);
        System.arraycopy(index, 0, result, 20, 4);
        System.arraycopy(length, 0, result, 24, 4);
        System.arraycopy(param.getBytes(), 0, result, 28, param.getBytes().length);

        return result;
    }

    public static final byte[] intToByteArray_Little(int value) {
        return new byte[]{(byte) value, (byte) (value >>> 8), (byte) (value >>> 16), (byte) (value >>> 24)};
    }
}
