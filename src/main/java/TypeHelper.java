public class TypeHelper {
    final private static char[] HEXTAB =
            {
                    '0', '1', '2', '3',	'4', '5', '6', '7',
                    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
            };

    /**
     * byteArray를 Hex String으로 변환
     * @param bytes : 변환하고자하는 Byte Array
     * @return : Hex로 변환된 String
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            sb.append(String.format("%02x", b&0xff));
        }
        return sb.toString();
    }

    /**
     * byteArray를 Hex String으로 변환
     * @param bytes : 변환하고자하는 Byte Array
     * @param offset : 변환 시작 위치
     * @param convLen : 변환할 길이
     * @return : Hex로 변환된 String
     */
    public static String byteArrayToHexString(byte[] bytes, int offset, int convLen) {
        int inx = 0;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            //이동하고자 하는 길이(offset)만큼 이동이 하고 변환.
            if(offset > inx)
            {
                inx++;
                continue;
            }
            sb.append(String.format("%02x", b&0xff));
            count++;
            //변환하고자 하는 길이(convLen)만큼 변환이 되면 빠져나간다.
            if (count>=convLen)
                break;
        }
        return sb.toString();
    }

    static public String intToLeftZeroFillString(int totalLength, int value) {
        return String.format("%0" + totalLength + "d", value);
    }
    static public String intToLeftSpaceFillString(int totalLength, int value) {
        return String.format("%" + totalLength + "d", value);
    }
    static public String intToRightZeroFillString(int totalLength, int value) {
        return String.format("%-0" + totalLength + "d", value);
    }
    static public String intToRightSpaceFillString(int totalLength, int value) {
        return String.format("%-" + totalLength + "d", value);
    }
    static public String intToAmountString(int totalLength, int value) {
        return String.format("%," + totalLength + "d", value);
    }
    static public String intToAmountString(int value) {
        return String.format("%,d", value);
    }
    static public String longToAmountString(int totalLength, long value) {
        return String.format("%," + totalLength + "d", value);
    }
    static public String longToAmountString(long value) {
        return String.format("%,d", value);
    }

    static public String stringLeftSpaceFillString(int totalLength, String value) {
        return String.format("%" + totalLength + "s", value);
    }

    static public String stringRightSpaceFillString(int totalLength, String value) {
        return String.format("%-" + totalLength + "s", value);
    }

    static public String stringLeftCharFillString(int totalLength, char fillChar, String value) {
        return String.format("%" + totalLength + "s", value).replace(' ', fillChar);
    }

    static public String stringRightCharFillString(int totalLength, char fillChar, String value) {
        return String.format("%-" + totalLength + "s", value).replace(' ', fillChar);
    }

    static public void bytesCopySpaceFillRight(byte[] dest, int pos, String value, int totalLength)
    {
        System.arraycopy(stringRightSpaceFillString(totalLength,value).getBytes(), 0, dest, pos, totalLength);
    }

    static public void bytesCopySpaceFillRight(byte[] dest, int pos, byte[] value, int totalLength)
    {
        if (value.length==totalLength) {
            System.arraycopy(value, 0, dest, pos, totalLength);
        } else {
            System.arraycopy(stringRightSpaceFillString(totalLength, new String(value)).getBytes(), 0, dest, pos, totalLength);
        }
    }

    static public void bytesCopy(byte[] dest, int pos, byte[] value, int totalLength)
    {
        System.arraycopy(value, 0, dest, pos, totalLength);
    }

    static public void bytesCopy(byte[] src, int srcPos, byte[] dest, int destPos, int totalLength)
    {
        System.arraycopy(src, srcPos, dest, destPos, totalLength);
    }

    static public boolean stringCopyBytes(byte[] dest, int pos, int totalLength, String value, char fillChar)
    {
        if (value.length() != totalLength) {
            for (int i=0; i<totalLength; i++)
                dest[pos+i] = (byte)fillChar;
            return false;
        }
        System.arraycopy(value.getBytes(), 0, dest, pos, totalLength);
        return true;
    }
    static public void bytesSpaceFill(byte[] dest, int destPos, int totalLength) {
        String value = String.format("%-" + totalLength + "s", " ");
        System.arraycopy(value.getBytes(), 0, dest, destPos, totalLength);
    }
    static public void intToLeftZeroFillBytes(byte[] dest, int pos, int totalLength, int value) {
        String sValue = String.format("%0" + totalLength + "d", value);
        bytesCopy(dest, pos, sValue.getBytes(), totalLength);
    }

    static public void stringToLeftCharFillBytes(byte[] dest, int pos, int totalLength, String value, char fillChar) {
        String sValue = String.format("%" + totalLength + "s", value).replace(' ', fillChar);
        bytesCopy(dest, pos, sValue.getBytes(), totalLength);
    }

    static public void stringToRightCharFillBytes(byte[] dest, int pos, int totalLength, String value, char fillChar) {
        String sValue = String.format("%-" + totalLength + "s", value).replace(' ', fillChar);
        bytesCopy(dest, pos, sValue.getBytes(), totalLength);
    }

    static public byte[] stringToRightCharFillBytes(int totalLength, String value, char fillChar) {
        byte[] targetBuf = new byte[totalLength];
        String sValue = String.format("%-" + totalLength + "s", value).replace(' ', fillChar);
        TypeHelper.stringCopyBytes(targetBuf, 0, totalLength, sValue, fillChar);
        return targetBuf;
    }

    /**
     * 목적지 byte배열에 byte배열을 복사한 후 복사한 만큼 옵셋을 추가햐여 리턴
     * @param dest : 복사할 대상
     * @param destOffset : 복사 시작 위치
     * @param src : 복사할 내용
     * @param srcLength : 복사할 길이
     * @return : 기존 옵셋값에 복사한 길이 더해서 리턴
     */
    static public int writeBytes(byte[] dest, int destOffset, byte[] src, int srcLength)
    {
        //src가 null이면 공백으로 채워서 리턴
        if (src==null) {
            bytesSpaceFill(dest, destOffset, srcLength);
            return (destOffset + srcLength);
        }
        if (src.length<srcLength) {
            bytesCopySpaceFillRight(dest, destOffset, src, srcLength);
        } else {
            bytesCopy(dest, destOffset, src, srcLength);
        }
        return (destOffset + srcLength);
    }
    /**
     * 목적지 byte배열에 byte배열을 복사한 후 복사한 만큼 옵셋을 추가햐여 리턴
     * @param dest : 복사할 대상
     * @param destOffset : 복사 시작 위치
     * @param value : 정수값을 byte배열로 변환, 남는 자리는 0으로 채운다
     * @param srcLength : 복사할 길이
     * @return : 기존 옵셋값에 복사한 길이 더해서 리턴
     */
    static public int writeBytes(byte[] dest, int destOffset, int value, int srcLength)
    {
        intToLeftZeroFillBytes(dest, destOffset, srcLength, value);
        return (destOffset + srcLength);
    }
    /**
     * 목적지 byte배열에 byte를 복사한 후 복사한 만큼 옵셋을 추가햐여 리턴
     * @param dest : 복사할 대상
     * @param destOffset : 복사 시작 위치
     * @param src : 복사할 내용
     * @return : 기존 옵셋값에 복사한 길이 더해서 리턴
     */
    static public int writeByte(byte[] dest, int destOffset, byte src)
    {
        dest[destOffset] = src;
        return (destOffset + 1);
    }

    public synchronized static void writeHexDump(byte[] srcBuf, int srcLength)
    {
        //NSData
        //@synchronized(buffer)
        byte[] buf = srcBuf; //copy, so parameter will also copy.
        int length = srcLength;
    /*String buf2 = new String(buf);
		System.out.println(buf2);*/

        System.out.print("------------------------------------------------------------------------------\n\r");
        int i = 0;

        if (length == 0) return;

        int j = 0;
        System.out.print(String.format("%4d: ", i + 1));

        char[] printBuf = new char[2];

        while (i < length) {
            System.out.print(String.format("%02x ", buf[i]));

            if (j == 7) System.out.print("  ");

            if (j == 15) {
                System.out.print("|");

                for (int k = i - 15; k < i + 1; k++) {
                    if (buf[k] > 0x7f) {
                        if (buf[k + 1] > 127) {
                            printBuf[0] = (char) buf[k];
                            printBuf[1] = (char) buf[k + 1];
                            String strBuf = new String(printBuf);
                            System.out.print(strBuf);
                            k += 2;
                        } else System.out.print(".");
                    } else {
                        if (buf[k] < 0x20 ) System.out.print(".");
                        else {
                            System.out.print((char) buf[k]);
                        }
                    }
                }
                System.out.print("\r\n");
                System.out.print(String.format("%4d: ", i + 2));
                j = 0;

            } else j++;

            i++;
        }

        if (j < 16) {
            int count = (16 - j) * 3;

            //separation after 8 byte
            if (j < 8) count += 2;

            for (int k = 0; k < count; k++)
                System.out.print(" ");
            System.out.print("|");

            //print character(if it is not in ASCII, just print ".")
            for (int k = i - j; k < i; k++) {
                System.out.print((buf[k] > 127 || buf[k] == 0x00) ? "." : (char) buf[k]);
            }
        }

        System.out.print("\r\n");
        System.out.print("------------------------------------------------------------------------------\n\r");

    }

}
