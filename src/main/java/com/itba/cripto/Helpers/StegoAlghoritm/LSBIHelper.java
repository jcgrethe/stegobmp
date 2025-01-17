package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Helpers.EncryptionModes.RC4ModeHelper;
import com.itba.cripto.Helpers.Utils.Convertions;
import com.itba.cripto.Interfaces.SteganographyAlgorithm;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;

public class LSBIHelper implements SteganographyAlgorithm {


    private int currentByte = 0;
    private int currentTextByte = 0;
    private int keySize = 6;
    private int jump;
    private byte[] key;
    private RC4ModeHelper rc4;

    private int bitPosition = 8;
    private int fileBytePosition = 0;
    private int currentImageByte = 0;

    private int imageSize;
    private byte[] data;
    private ByteBuffer total;
    private byte[] resp;
    private ByteBuffer imageSizeByte;

    @Override
    public String getExtension(byte[] img) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {
        int count = imageSize + 4;
        ByteBuffer extension = ByteBuffer.allocate(100);
        int size = 0;
        ByteBuffer totalData = ByteBuffer.allocate(total.array().length + 10);
        totalData.put(imageSizeByte.array());
        totalData.put(resp);
        do {
            byte nextByte = getNextByte(data, jump);
            totalData.put(nextByte);
            nextByte = rc4.decrypt(totalData.array(), key)[count];
            if (nextByte == 0) {
                break;
            }
            count++;
            size++;
            extension.put(nextByte);
        } while (true);
        return new String(Arrays.copyOfRange(extension.array(), 0, size));
    }

    public byte[] hide(byte[] img, byte[] file) throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        rc4 = new RC4ModeHelper();
        key = Arrays.copyOfRange(img, 0, keySize);
        jump = getJump(img[0]);

        byte[] cipherText = rc4.encrypt(file, key);
        byte[] data = Arrays.copyOfRange(img, keySize, img.length);

        while (fileBytePosition < cipherText.length) {
            data[currentImageByte] = setNextByte(data[currentImageByte], cipherText);
            currentImageByte += jump;
            if (currentImageByte >= data.length - 1) {
                currentImageByte = currentImageByte % (data.length + keySize - 1);
            }
        }
        byte[] resp = new byte[img.length];
        System.arraycopy(key, 0, resp, 0, key.length);
        System.arraycopy(data, 0, resp, key.length, data.length);
        return resp;
    }

    public byte[] looking(byte[] img) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException {

        rc4 = new RC4ModeHelper();
        key = Arrays.copyOfRange(img, 0, keySize);
        data = Arrays.copyOfRange(img, keySize, img.length);
        jump = getJump(img[0]);

        imageSizeByte = ByteBuffer.allocate(IMAGEBYTESSIZE);

        for (int i = 0; i < IMAGEBYTESSIZE; i++) {
            imageSizeByte.put(getNextByte(data, jump));
        }
        imageSizeByte.flip();
        imageSize = ByteBuffer.wrap(rc4.decrypt(imageSizeByte.array(), key)).getInt();

        resp = new byte[imageSize];

        for (int i = 0; i < imageSize; i++) {
            resp[currentTextByte++] = getNextByte(data, jump);
        }

        total = ByteBuffer.allocate(imageSize + 4);
        total.put(imageSizeByte.array());
        total.put(resp);
        byte[] decrypt = rc4.decrypt(total.array(), key);


        return Arrays.copyOfRange(decrypt, IMAGEBYTESSIZE, decrypt.length);
    }

    @Override
    public long maxSize(int size) {
        return (size - keySize) / 8;
    }

    private byte getNextByte(byte[] data, int jump) {
        StringBuilder buffer = new StringBuilder();
        for (int j = 0; j < 8; j++) {
            buffer.append(Convertions.getBit(0, data[currentByte]));
            currentByte = (currentByte + jump) % (data.length + keySize - 1);
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

    private byte setNextByte(byte current, byte[] file) {
        byte aux;
        if (file.length > fileBytePosition) {
            aux = Convertions.ChangeBit(current, 0, Convertions.getBit(--bitPosition, file[fileBytePosition]));

            if (bitPosition == 0) {
                bitPosition = 8;
                fileBytePosition++;
            }
        } else {
            aux = current;
        }
        return aux;
    }
}
