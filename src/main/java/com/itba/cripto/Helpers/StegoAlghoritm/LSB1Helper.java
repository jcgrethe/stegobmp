package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Helpers.Utils.Convertions;
import com.itba.cripto.Interfaces.SteganographyAlgorithm;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;

public class LSB1Helper implements SteganographyAlgorithm {

    private int currentByte = 0;
    private int currentTextByte = 0;
    private int bitPosition = 8;
    private int fileBytePosition = 0;

    @Override
    public String getExtension(byte[] img) {
        int count = 0;
        ByteBuffer extension = ByteBuffer.allocate(10);
        do {
            byte nextByte = getNextByte(img);
            if (nextByte == 0) {
                break;
            }
            count++;
            extension.put(nextByte);
        } while (true);
        return new String(Arrays.copyOfRange(extension.array(), 0, count));
    }

    public byte[] hide(byte[] img, byte[] file) {

        int imageSize = img.length;
        byte[] resp = img;

        for (int i = 0; i < imageSize; i++) {
            resp[i] = setNextByte(img[i], file);
        }
        return resp;
    }

    public byte[] looking(byte[] img) {

        int imageSize;
        ByteBuffer imageSizeByte = ByteBuffer.allocate(IMAGEBYTESSIZE);

        for (int i = 0; i < IMAGEBYTESSIZE; i++) {
            imageSizeByte.put(getNextByte(img));
        }
        imageSizeByte.flip();
        imageSize = imageSizeByte.getInt();

        if (imageSize > (maxSize(img.length) - currentByte - 1) || imageSize <= 0) {
            throw new IllegalArgumentException("Illegal size");
        }

        byte[] resp = new byte[imageSize];

        for (int i = 0; i < imageSize; i++) {
            resp[currentTextByte++] = getNextByte(img);
        }
        return resp;
    }

    @Override
    public long maxSize(int size) {
        return size / 8;
    }

    private byte getNextByte(byte[] img) {
        StringBuilder buffer = new StringBuilder();
        for (int j = 0; j < 8; j++) {
            buffer.append(Convertions.getBit(0, img[currentByte]));
            currentByte++;
        }
        //uso parse int en binario si es unsigned. Byte.parse me lo devuelve signed.
        return (byte) Integer.parseInt(buffer.toString(), 2);
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
