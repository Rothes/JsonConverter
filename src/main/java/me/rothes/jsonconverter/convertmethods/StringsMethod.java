package me.rothes.jsonconverter.convertmethods;

import me.rothes.jsonconverter.EnLocale;
import me.rothes.jsonconverter.JsonConverter;
import me.rothes.jsonconverter.utils.FileUtils;

import java.io.File;
import java.util.HashMap;

public class StringsMethod implements ConvertMethod {

    private int i = 0;

    @Override
    public HashMap<String, EnLocale> getEnLocalization() {
        System.out.println("输入 strings 文件路径:");
        File file = new File(JsonConverter.getScanner().nextLine());
        if (!FileUtils.isExist(file)) {
            return null;
        }
        HashMap<String, EnLocale> map = new HashMap<>();

        // 读取 strings
        String strings = FileUtils.readFile(file);
        String[] sp = strings.split("\n");
        System.out.println("--------------------------------------------------------------------------------");
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (i != sp.length) {
                    System.out.print("处理中.. 行数 " + i + " / " + sp.length + "\r");
                    try {
                        Thread.sleep(20L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    run();
                }
            }
        };
        thread.start();
        while (i < sp.length - 1) {
            String line = getPlainText(sp[i]);
            String next = getPlainText(sp[++i]);
            map.put(line, new EnLocale(next, EnLocale.LocaleType.STRING));
        }
        System.out.println("--------------------------------------------------------------------------------");
        return map;
    }

    private String getPlainText(String str) {
        int i = 0;
        for (; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                break;
            }
        }
        String result = str.substring(i);
        if (result.startsWith("\"")) {
            result = result.substring(1);
        }
        if (result.endsWith("\",")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

}
