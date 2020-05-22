package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Helpers.Utils.Convertions;
import com.itba.cripto.Interfaces.AlgoritmosEsteganografiado;

import java.nio.ByteBuffer;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;

public class LSB1Helper implements AlgoritmosEsteganografiado {

    int currentByte = 0;
    int currentTextByte = 0;
    int bitPosition = 0;
    int fileBytePosition = 0;

    public byte[] hide(byte[] img, byte[] file) {

        int imageSize = img.length;
        byte[] resp = img;

        for (int i = 0; i < imageSize; i++) {
            resp[currentByte] = setNextByte(img, file);
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

        byte[] resp = new byte[imageSize];

        for (int i = 0; i < imageSize; i++) {
            resp[currentTextByte++] = getNextByte(img);
        }
        return resp;
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

    private byte setNextByte(byte[] img, byte[] file) {

        byte aux = Convertions.ChangeBit(img[currentByte++], 0, Convertions.getBit(bitPosition++, file[fileBytePosition]));

        if (bitPosition == 8) {
            bitPosition = 0;
            fileBytePosition++;
        }

        return aux;
    }

}
