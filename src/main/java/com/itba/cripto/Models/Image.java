package com.itba.cripto.Models;

import lombok.Getter;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.HEADER_SIZE;

@Getter
public class Image {


    private byte[] imageData;
    private byte[] imageHeader;

    public Image(String path) throws IOException {
        if (path == null)
            throw new IllegalArgumentException();
        byte[] imageArray = IOUtils.toByteArray(new FileInputStream(path));
        imageHeader = Arrays.copyOfRange(imageArray, 0, HEADER_SIZE);
        imageData = Arrays.copyOfRange(imageArray, HEADER_SIZE, imageArray.length);
    }


}
