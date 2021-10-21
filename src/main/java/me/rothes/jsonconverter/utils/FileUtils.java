package me.rothes.jsonconverter.utils;

import me.rothes.jsonconverter.JsonConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String readFile(File file) {
        final StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static boolean isExist(File file) {
        if (!file.exists()) {
            System.out.println("文件不存在: " + file.getAbsolutePath());
            JsonConverter.sleep(1000L);
            return false;
        }
        return true;
    }

}
