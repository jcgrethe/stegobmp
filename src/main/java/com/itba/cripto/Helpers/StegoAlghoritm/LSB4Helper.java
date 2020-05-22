package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Helpers.Utils.Convertions;
import com.itba.cripto.Interfaces.AlgoritmosEsteganografiado;
import com.itba.cripto.Models.Image;

import java.nio.ByteBuffer;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;
import static com.itba.cripto.Helpers.Utils.Convertions.getBit;

public class LSB4Helper implements AlgoritmosEsteganografiado {

    int currentByte = 0;
    int bitPosition = 0;
    int fileBytePosition = 0;

    public byte[] Hide(byte[] img, byte[] file) {

        int imageSize = img.length;
        byte[] resp = img;

        for (int i = 0; i < imageSize; i++) {
            resp[currentByte] = setNextByte(img, file);
        }
        return resp;
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
        for (int j = 0; j < 2; j++) {
            buffer.append(getBit(3, img[currentByte]));
            buffer.append(getBit(2, img[currentByte]));
            buffer.append(getBit(1, img[currentByte]));
            buffer.append(getBit(0, img[currentByte]));
            currentByte++;
        }
        //uso parse int en binario si es unsigned. Byte.parse me lo devuelve signed.
        return (byte) Integer.parseInt(buffer.toString(), 2);
    }

    private byte setNextByte(byte[] img, byte[] file) {

        byte auxResp = img[currentByte];
        for(int i = 0; i < 4; i++) {
            auxResp = Convertions.ChangeBit(auxResp, i, getBit(bitPosition++, file[fileBytePosition]));
        }
        currentByte++;
        if (bitPosition == 8) {
            bitPosition = 0;
            fileBytePosition++;
        }

        return auxResp;
    }


}
