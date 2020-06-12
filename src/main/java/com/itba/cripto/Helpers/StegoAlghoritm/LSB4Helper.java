package com.itba.cripto.Helpers.StegoAlghoritm;


import com.itba.cripto.Helpers.Utils.Convertions;
import com.itba.cripto.Interfaces.SteganographyAlgorithm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;
import static com.itba.cripto.Helpers.Utils.Convertions.getBit;

public class LSB4Helper implements SteganographyAlgorithm {

    int currentByte = 0;
    int bitPosition = 8;
    int fileBytePosition = 0;

    public byte[] hide(byte[] img, byte[] file) {

        int imageSize = img.length;
        byte[] resp = img;

        for (int i = 0; i < imageSize; i++) {
            resp[i] = setNextByte(img[i], file);
        }
        return resp;
    }

    public byte[] looking(byte[] img) {

        int currentTextByte = 0;
        int imageSize;
        ByteBuffer imageSizeByte = ByteBuffer.allocate(IMAGEBYTESSIZE).order(ByteOrder.BIG_ENDIAN);

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

    public byte getNextByte(byte[] img) {
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

    private byte setNextByte(byte current, byte[] file) {
        byte aux = current;
        if(file.length > fileBytePosition) {
            for (int i = 3; i >=0 ; i--) {
                aux = Convertions.ChangeBit(aux, i, Convertions.getBit(--bitPosition, file[fileBytePosition]));
            }

            if (bitPosition == 0) {
                bitPosition = 8;
                fileBytePosition++;
            }
        }else {
            aux = current;
        }

        return aux;
    }


}
