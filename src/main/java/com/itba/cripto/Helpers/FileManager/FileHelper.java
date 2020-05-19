package com.itba.cripto.Helpers.FileManager;

import com.itba.cripto.Models.Image;
import lombok.Builder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Builder
public class FileHelper {

    final private String inPath;
    final private String outPath;
    final private String imagePath;
    private Image image = null;

    public Image getImage() throws IOException {
        if(image != null)
            return image;
        image = new Image(imagePath);
        return image;
    }

    public String getText() throws IOException {
        Path path = Paths.get(inPath);
        return Files.readAllLines(path).get(0);
    }

    public void saveImage(Image image) throws IOException {
        FileOutputStream fos = new FileOutputStream(outPath);
        byte[] allByteArray = new byte[image.getImageHeader().length + image.getImageData().length];

        ByteBuffer buff = ByteBuffer.wrap(allByteArray);
        buff.put(image.getImageHeader());
        buff.put(image.getImageData());

        fos.write(buff.array());
    }

    public void saveData(byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(data);

    }


}
