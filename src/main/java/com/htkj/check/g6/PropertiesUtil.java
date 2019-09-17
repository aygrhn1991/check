package com.htkj.check.g6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties p = new Properties();
    public static String path;

    public static void init(String filepath) {
        path = filepath;
        //转换成流
        try {
            InputStream inputStream = new FileInputStream(path);
            //从输入流中读取属性列表（键和元素对）
            p.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return p.getProperty(key);
    }

    public static void update(String key, String value) {
        p.setProperty(key, value);
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream(path);
            //将Properties中的属性列表（键和元素对）写入输出流
            p.store(oFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete(String key) {
        p.remove(key);
        FileOutputStream oFile = null;
        try {
            oFile = new FileOutputStream(path);
            p.store(oFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void list() {
        Enumeration en = p.propertyNames(); //得到配置文件的名字
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = p.getProperty(strKey);
            System.out.println(strKey + "=" + strValue);
        }
    }
}
