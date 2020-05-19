package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Interfaces.AlgoritmosEsteganografiado;

import java.nio.ByteBuffer;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;

public class LSB1Helper implements AlgoritmosEsteganografiado {


    int currentByte = 0;

    public byte[] Hide(byte[] img, byte[] file) {

        return null;
    }

    public byte[] Looking(byte[] img) {

        int currentTextByte = 0;
        int imageSize;
        ByteBuffer imageSizeByte = ByteBuffer.allocate(IMAGEBYTESSIZE);

        for (int i = 0; i < IMAGEBYTESSIZE; i++) {
            imageSizeByte.put(getNextByte(img));
        }
        imageSizeByte.flip();
        imageSize = imageSizeByte.getInt();

        byte[] resp = new byte[imageSize];

        for (int i = 0; i < imageSize; i++) {
            resp[currentTextByte++] = getNextByte(img);
        }
        return resp;
    }

    private byte getNextByte(byte[] img) {
        StringBuilder buffer = new StringBuilder();
        for (int j = 0; j < 8; j++) {
            buffer.append(getBit(0, img[currentByte]));
            currentByte++;
        }
        //uso parse int en binario si es unsigned. Byte.parse me lo devuelve signed.
        return (byte) Integer.parseInt(buffer.toString(), 2);
    }

    public int getBit(int position, int num) {
        return (num >> position) & 1;
    }


}
