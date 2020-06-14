package com.itba.cripto.Helpers.FileManager;

import com.itba.cripto.Models.Image;
import lombok.Builder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
