package com.pnj.file;

import java.io.*;

/**
 * <p>Description: 测试类</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 * <p>Company: SenseTime</p>
 * <p>Email: pengnanjing@sensetime.com</p>
 *
 * @author nanjing
 * @date 17-5-15:下午2:17
 */
public class FileUtil {

    public static String getName(String str) {
        return str;
    }

    public static void append(File f) throws IOException {
        FileWriter fileWriter = new FileWriter(f, true);
        fileWriter.append("xxxx");
        fileWriter.flush();

    }
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
