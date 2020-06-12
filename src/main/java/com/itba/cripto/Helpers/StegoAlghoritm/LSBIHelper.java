package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Helpers.EncryptionModes.RC4ModeHelper;
import com.itba.cripto.Helpers.Utils.Convertions;
import com.itba.cripto.Interfaces.SteganographyAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;

public class LSBIHelper implements SteganographyAlgorithm {


    int currentByte = 0;
    int currentTextByte = 0;
    int keySize = 6;
    int count = 0;
    private int jump;


    @Override
    public String getExtension(byte[] img) {
        int count = 0;
        ByteBuffer extension = ByteBuffer.allocate(10);
        do{
            byte nextByte = getNextByte(img, jump);
            if(nextByte == 0){
                break;
            }
            count++;
            extension.put(nextByte);
        }while (true);
        return new String(Arrays.copyOfRange(extension.array(),0,count));
    }

    public byte[] hide(byte[] img, byte[] file) {
        return null;
    }

    public byte[] looking(byte[] img) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {

        RC4ModeHelper rc4 = new RC4ModeHelper();
        final byte[] key = Arrays.copyOfRange(img, 0, keySize);
        byte[] data = Arrays.copyOfRange(img, keySize, img.length);
        jump = getJump(img[0]);

        int imageSize;
        ByteBuffer imageSizeByte = ByteBuffer.allocate(IMAGEBYTESSIZE);

        for (int i = 0; i < IMAGEBYTESSIZE; i++) {
            imageSizeByte.put(getNextByte(data, jump));
        }
        imageSizeByte.flip();
        imageSize = ByteBuffer.wrap(rc4.decrypt(imageSizeByte.array(), key)).getInt();

        byte[] resp = new byte[imageSize];

        for (int i = 0; i < imageSize; i++) {
            resp[currentTextByte++] = getNextByte(data, jump);
        }

        ByteBuffer total = ByteBuffer.allocate(imageSize + 4);
        total.put(imageSizeByte.array());
        total.put(resp);
        byte[] decrypt = rc4.decrypt(total.array(), key);
        return Arrays.copyOfRange(decrypt, IMAGEBYTESSIZE, decrypt.length);
    }

    private byte getNextByte(byte[] data, int jump) {
        StringBuilder buffer = new StringBuilder();
        count++;
        for (int j = 0; j < 8; j++) {
            buffer.append(Convertions.getBit(0, data[currentByte]));
            currentByte = (currentByte + jump);
            if (currentByte >= data.length - 1) {
                System.out.println(count);
                System.out.println(currentByte);
                currentByte = currentByte - data.length;
                System.out.println(currentByte);
            }
        }
        //uso parse int en binario si es unsigned. Byte.parse me lo devuelve signed.
        return (byte) Integer.parseInt(buffer.toString(), 2);
    }

    private int getJump(byte jumpByte) {
        for (int j = 7; j >= 0; j--) {
            if (Convertions.getBit(j, jumpByte) == 1) {
                return (int) Math.pow(2, j);
            }
        }
        return 256;
    }

}
