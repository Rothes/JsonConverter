package me.rothes.jsonconverter;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.rothes.jsonconverter.convertmethods.CodesMethod;
import me.rothes.jsonconverter.convertmethods.ConvertMethod;
import me.rothes.jsonconverter.convertmethods.StringsMethod;
import me.rothes.jsonconverter.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class JsonConverter {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("输入待转换的语言 json 路径:");
        File file = new File(getScanner().nextLine());
        if (!FileUtils.isExist(file)) {
            return;
        }

        boolean doUpgrade = false;
        System.out.println("输入旧版本 json 文件路径, 如不需要升级请留空直接按回车:");
        String path = getScanner().nextLine();
        File upgradeFile = null;
        if (!path.isEmpty()) {
            upgradeFile = new File(path);
            doUpgrade = true;
            if (!FileUtils.isExist(upgradeFile)) {
                return;
            }
        }
        String json = FileUtils.readFile(file);
        ConvertMethod method;
        System.out.println("选择转换方式:\n'1': 通过导出的 Codes 转换.(推荐)\n'2': 通过导出的 Strings 转换.");
        switch (getScanner().nextLine()) {
            case "1":
                method = new CodesMethod();
                break;
            case "2":
                method = new StringsMethod();
                break;
            default:
                System.out.println("输入值无效.");
                sleep(100L);
                return;
        }

        HashMap<String, EnLocale> map = method.getEnLocalization();
        if (map == null) {
            return;
        }

        // 读取旧语言 json
        HashMap<String, String> upgrade = new HashMap<>();
        if (doUpgrade) {
            JsonElement jsonElement = JsonParser.parseString(FileUtils.readFile(upgradeFile));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Set<String> keySet = jsonObject.keySet();
            for (String key : keySet) {
                upgrade.put(key, jsonObject.getAsJsonPrimitive(key).getAsString());
            }
        }

        // 修改 json
        JsonElement jsonElement = JsonParser.parseString(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Set<String> keySet = jsonObject.keySet();
        String value;
        String old;
        EnLocale enLocale;
        for (String key : keySet) {
            old = upgrade.get(key);
            if (old != null) {
                value = old;
            } else {
                enLocale = map.get(key);
                if (enLocale == null) {
                    System.out.println("未找到此键: " + key);
                    continue;
                } else if (enLocale.getType() == EnLocale.LocaleType.LANG_KEY) {
                    System.out.println("此键需要提供英文 Json: " + key);
                    continue;
                }
                value = enLocale.getValue();
            }

            jsonObject.addProperty(key, value);
        }

        // 保存文件
        try {
            String result = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping() /* 格式化 */
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

    public static Scanner getScanner() {
        return scanner;
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
