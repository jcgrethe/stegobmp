package com.itba.cripto;

import com.itba.cripto.Helpers.Factories.ActionFactory;
import com.itba.cripto.Helpers.Factories.EncriptionModeFactory;
import com.itba.cripto.Models.EncriptionModeBase;
import org.apache.commons.cli.*;


public class App 
{
    public static void main( String[] args ){
        CommandLine cmd = getOptions(args);
        String msg = "HOLAAA!!!!";
        String key = "1234567891234567";
        String scheme = "des";
        EncriptionModeBase ActionToDo = ActionFactory.Action("-embed");
        ActionToDo.setEncripter(EncriptionModeFactory.Action("cbc"));
        String enc = ActionToDo.getEncripter().encrypt(msg,key,scheme);
        System.out.println(enc);
        String dec = ActionToDo.getEncripter().decrypt(enc,key,scheme);
        System.out.println(dec);

    }

    private static CommandLine getOptions(String[] args){

        Options options = new Options();

        Option embed = new Option("embed", "embed", false, "embed");
        embed.setRequired(false);
        options.addOption(embed);
        Option extract = new Option("extract", "extract", false, "extract");
        extract.setRequired(false);
        options.addOption(extract);
        Option in = new Option("in", "in", true, "bitmap file");
        in.setRequired(false);
        options.addOption(in);
        Option p = new Option("p", "p", true, "bitmap file");
        p.setRequired(false);
        options.addOption(p);
        Option out = new Option("out", "out", true, "bitmap file");
        out.setRequired(false);
        options.addOption(out);
        Option steg = new Option("steg", "steg", true, "<LSB1 | LSB4 | LSBI>");
        steg.setRequired(false);
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
        CommandLine cmd=null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        return cmd;
    }
}
