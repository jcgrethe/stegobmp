package com.itba.cripto;

import com.itba.cripto.Helpers.EncryptionModes.EncryptionModeHelper;
import com.itba.cripto.Helpers.Factories.AlgorithmsFactory;
import com.itba.cripto.Helpers.FileManager.FileHelper;
import com.itba.cripto.Interfaces.SteganographyAlgorithm;
import com.itba.cripto.Models.Image;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static com.itba.cripto.Helpers.Constant.Constants.ConstantsValues.IMAGEBYTESSIZE;


public class App {
    public static void main(String[] args) throws IOException {

        CommandLine cmd = getOptions(args);
        SteganographyAlgorithm steganographyAlgorithm = AlgorithmsFactory.type(cmd.getOptionValue("steg"));
        EncryptionModeHelper encryptionModeHelper = null;
        if (cmd.hasOption("pass")) {
            encryptionModeHelper = new EncryptionModeHelper(getEncryptionMode(cmd));
        }

        if (cmd.hasOption("embed")) {
            FileHelper fileHelper = FileHelper.builder()
                    .inPath(cmd.getOptionValue("in"))
                    .outPath(cmd.getOptionValue("out"))
                    .imagePath(cmd.getOptionValue("p"))
                    .build();

            Image image = fileHelper.getImage();
            byte[] fileToHide = fileHelper.getText().getBytes("UTF-8");
            byte[] size = ByteBuffer.allocate(4).putInt(fileToHide.length).array();
            byte[] file = ByteBuffer.allocate(fileToHide.length).put(fileToHide).array();
            byte[] hiddenFile = new byte[size.length + file.length];
            System.arraycopy(size, 0, hiddenFile, 0, size.length);
            System.arraycopy(file, 0, hiddenFile, size.length, file.length);

            String fileEncrypted = encryptionModeHelper.encrypt(hiddenFile,cmd.getOptionValue("pass"),cmd.getOptionValue("a"));

            byte[] sizeEnc = ByteBuffer.allocate(4).putInt(fileEncrypted.getBytes().length).array();
            byte[] fileEnc = ByteBuffer.allocate(fileEncrypted.getBytes().length).put(file).array();
            byte[] cipherText = new byte[sizeEnc.length + fileEnc.length];
            System.arraycopy(sizeEnc, 0, cipherText, 0, sizeEnc.length);
            System.arraycopy(fileEnc, 0, cipherText, sizeEnc.length, fileEnc.length);

            byte[] data = steganographyAlgorithm.hide(image.getImageData(), cipherText);


            byte[] imageData = new byte[data.length + image.getImageHeader().length];
            System.arraycopy(image.getImageHeader(), 0, imageData, 0, image.getImageHeader().length);
            System.arraycopy(data, 0, imageData, image.getImageHeader().length, data.length);
            fileHelper.saveData(imageData);


        } else if (cmd.hasOption("extract")) {
            FileHelper fileHelper = FileHelper.builder()
                    .inPath(cmd.getOptionValue("in"))
                    .outPath(cmd.getOptionValue("out"))
                    .imagePath(cmd.getOptionValue("p"))
                    .build();
            String key = cmd.getOptionValue("pass");

            Image image = fileHelper.getImage();
            byte[] data = steganographyAlgorithm.looking(image.getImageData());

            if (key != null) {
                byte[] dec = encryptionModeHelper.decrypt(data, key, getEncryptionAlgorithm(cmd));
                ByteBuffer imageSizeByte = ByteBuffer.allocate(IMAGEBYTESSIZE);
                for (int i = 0; i < IMAGEBYTESSIZE; i++) {
                    imageSizeByte.put(dec[i]);
                }
                imageSizeByte.flip();
                int imageSize = imageSizeByte.getInt();

                fileHelper.saveData(Arrays.copyOfRange(dec, 4, imageSize + 4));
            } else {
                fileHelper.saveData(data);
            }
        } else throw new IllegalArgumentException("embed extract");

    }

    private static CommandLine getOptions(String[] args) {

        Options options = new Options();

        if (args[0].equals("-embed")) {
            Option in = new Option("in", "in", true, "bitmap file");
            in.setRequired(true);
            options.addOption(in);
        } else if (!args[0].equals("-extract")) {
            throw new IllegalArgumentException();
        }

        Option embed = new Option("embed", "embed", false, "embed");
        embed.setRequired(false);
        options.addOption(embed);
        Option extract = new Option("extract", "extract", false, "extract");
        extract.setRequired(false);
        options.addOption(extract);


        Option p = new Option("p", "p", true, "bitmap file");
        p.setRequired(true);
        options.addOption(p);
        Option out = new Option("out", "out", true, "bitmap file");
        out.setRequired(true);
        options.addOption(out);
        Option steg = new Option("steg", "steg", true, "<LSB1 | LSB4 | LSBI>");
        steg.setRequired(true);
        options.addOption(steg);
        Option a = new Option("a", "a", true, "<aes128 | aes192 | aes256 | des>");
        a.setRequired(false);
        options.addOption(a);
        Option m = new Option("m", "m", true, " <ecb | cfb | ofb | cbc> ");
        m.setRequired(false);
        options.addOption(m);
        Option pass = new Option("pass", "pass", true, "password");
        pass.setRequired(false);
        options.addOption(pass);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        return cmd;
    }


    private static String getEncryptionAlgorithm(CommandLine cmd) {
        if (cmd.hasOption("a")) {
            return cmd.getOptionValue("a");
        }
        return "aes128";
    }

    private static String getEncryptionMode(CommandLine cmd) {
        if (cmd.hasOption("m")) {
            return cmd.getOptionValue("m");
        }
        return "cbc";
    }

}

