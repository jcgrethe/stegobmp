package com.itba.cripto.Helpers.FileManager;

import com.itba.cripto.Models.Image;
import lombok.Builder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public String getText() throws IOException {
        Path path = Paths.get(inPath);
        //TODO: revisar
        return Files.readAllLines(path).get(0);
    }

    public String getExtention(){
        Path path = Paths.get(inPath);
        String[] t1 = path.toString().split("/");
        String[] t4 = t1[t1.length-1].split("\\.");
        String fileExtention = t4[t4.length-1];
        return fileExtention;
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

    public void saveDataLooking(byte[] data,String extention) throws IOException{
        Path path = Paths.get(outPath);
        String[] t1 = path.toString().split("/");
        String[] t4 = t1[t1.length-1].split("\\.");
        String aux = t4[t4.length-1] + "." + extention;
        t1[t1.length-1] = aux;
        String outPathResp = String.join("/", t1);

        Path out = Paths.get(outPathResp.trim());

        FileOutputStream fos = new FileOutputStream(String.valueOf(out));
        fos.write(data);

    }


}
