package com.itba.cripto.Helpers.FileManager;

import com.itba.cripto.Models.Image;
import lombok.Builder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

@Builder
public class FileHelper {

    final private String inPath;
    final private String outPath;
    final private String imagePath;
    private Image image = null;

    public Image getImage() throws IOException {
        if (image != null)
            return image;
        image = new Image(imagePath);
        return image;
    }

    public byte[] getText() throws IOException {
        return IOUtils.toByteArray(new FileInputStream(inPath));
    }

    public String getExtention() {
        return FilenameUtils.getExtension(inPath);
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

    public void saveDataLooking(byte[] data, String extention) throws IOException {
        String out = outPath + extention;

        FileOutputStream fos = new FileOutputStream(out);
        fos.write(data);

    }


}
