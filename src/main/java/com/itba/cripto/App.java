package com.itba.cripto;

import com.itba.cripto.Helpers.Factories.ActionFactory;
import com.itba.cripto.Helpers.Factories.EncriptionModeFactory;
import com.itba.cripto.Helpers.FileManager.FileHelper;
import com.itba.cripto.Models.EncriptionModeBase;
import com.itba.cripto.Models.Image;
import org.apache.commons.cli.*;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {

        CommandLine cmd = getOptions(args);

        FileHelper fileHelper = FileHelper.builder()
                .inPath(cmd.getOptionValue("in"))
                .outPath(cmd.getOptionValue("out"))
                .imagePath(cmd.getOptionValue("p"))
                .build();

        Image image = fileHelper.getImage();

        fileHelper.saveImage(image);

/*        if (cmd.hasOption("embed")) {

            FileHelper fileHelper = FileHelper.builder()
                    .inPath(cmd.getOptionValue("in"))
                    .outPath(cmd.getOptionValue("out"))
                    .image(cmd.getOptionValue("p"))
                    .build();
            String msg = fileHelper.getText();
            String key = cmd.getOptionValue("pass");

            EncriptionModeBase actionToDo = ActionFactory.Action("-embed");
            actionToDo.setEncrypter(EncriptionModeFactory.Action(getEncryptionMode(cmd)));

            String enc = actionToDo.getEncrypter().encrypt(msg, key, getEncryptionAlgorithm(cmd));
            System.out.println(enc);



            String dec = actionToDo.getEncrypter().decrypt(enc, key, getEncryptionAlgorithm(cmd));
            System.out.println(dec);
        } else if (cmd.hasOption("extract")) {

        } else throw new IllegalArgumentException("embed extract");*/

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


    private static String getEncryptionAlgorithm(CommandLine cmd){
        if(cmd.hasOption("a")){
            return cmd.getOptionValue("a");
        }
        return "aes128";
    }

    private static String getEncryptionMode(CommandLine cmd){
        if(cmd.hasOption("m")){
            return cmd.getOptionValue("m");
        }
        return "cbc";
    }

}

