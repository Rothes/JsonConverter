package me.rothes.jsonconverter;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class JsonConverter {

    static Pattern start = Pattern.compile("^[ ]*");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("输入 strings 路径:");
        File file = new File(sc.nextLine());
        if (!file.exists()) {
            System.out.println("文件不存在: " + file.getAbsolutePath());
            sleep(10000L);
            return;
        }
        String strings = readFile(file);
        System.out.println("输入 json 路径:");
        file = new File(sc.nextLine());
        if (!file.exists()) {
            System.out.println("文件不存在: " + file.getAbsolutePath());
            sleep(10000L);
            return;
        }
        String json = readFile(file);
        sc.close();

        // 读取 strings
        HashMap<String, String> map = new HashMap<>();
        String[] sp = strings.split("\n");
        for (int i = 0; i < sp.length - 1;) {
            String line1 = getPlainText(sp[i]);
            String next1 = getPlainText(sp[++i]);
            map.put(line1, next1);
        }

        // 修改 json
        JsonElement jsonElement = JsonParser.parseString(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            String value = map.get(key);
            if (value == null) {
                System.out.println("strings不存在此键: " + key);
                continue;
            }
            // 防止误改
            if (keySet.contains(value)) {
                continue;
            }

            jsonObject.addProperty(key, value);
        }

        // 保存文件
        try {
            String result = new GsonBuilder().setPrettyPrinting() /* 格式化 */
                    .create().toJson(jsonElement);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
            writer.write(result);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("已完成处理.");
        sleep(15000L);
    }

    private static String readFile(File file) {
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

    private static String getPlainText(String str) {
        String result = start.matcher(str).replaceAll("");
        if (result.startsWith("\"")) {
            result = result.substring(1);
        }
        if (result.endsWith("\",")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
