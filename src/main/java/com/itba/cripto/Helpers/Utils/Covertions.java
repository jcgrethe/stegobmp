package com.itba.cripto.Helpers.Utils;

import java.math.BigInteger;
import java.nio.charset.Charset;

public class Covertions {

    public static String hexStringFromBytes(byte[] b)
    {
        char[] hexChars={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String hex = "";
        int msb;
        int lsb = 0;
        int j;
        for (j = 0; j < b.length; j++)
        {
            msb = ((int)b[j] & 0x000000FF) / 16;
            lsb = ((int)b[j] & 0x000000FF) % 16;
            hex = hex + hexChars[msb] + hexChars[lsb];
        }
        return(hex);
    }

    public static byte ChangeBit(byte b, int position, int bitToSet){
        byte resp = b;
        switch (bitToSet){
            case 1: resp |= 1 << position;return resp;
            case 0: resp &= ~(1 << position);return resp;
        }
        return resp = -1;
    }

    public static int GetBit(byte b, int position){
        return (b >> position) & 1;
    }
}
