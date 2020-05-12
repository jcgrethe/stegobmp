package com.itba.cripto.Helpers.FileManager;

import lombok.Builder;
import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Builder
public class FileHelper {

    final private String inPath;
    final private String outPath;
    final private String image;

    public String getText() throws IOException {
        Path path = Paths.get(inPath);
        String read = Files.readAllLines(path).get(0);
        return read;
    }





}
