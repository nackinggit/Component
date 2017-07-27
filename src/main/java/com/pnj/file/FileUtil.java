package com.pnj.file;

import java.io.*;
import java.nio.file.Files;

/**
 * <p>Description: 测试类</p>
 * <p>Copyright: Copyright  (c) 2017</p>
 *
 * @author nanjing
 * @date 17-5-15:下午2:17
 */
public class FileUtil {

    public static FileWriter fileWriter;

    public static String getName(String str) {
        return str;
    }

    public static void append(File f, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(f, true);
        fileWriter.append(content);
        fileWriter.flush();
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException {
        File f = new File("/home/nanjing/Desktop/testFile.txt");
        RandomAccessFile fis = new RandomAccessFile(f, "rw");
        byte[] bytes = new byte[1024];
        int i = 0;
        int j = 0;
        while (j++ != 1000000000) {
            System.out.println(fis.readLine());
        }


//        FileWriter fileWriter = new FileWriter(f, true);
//        int i = 0;
//        while (i++ != 1000000000) {
//            fileWriter.append("hello" + i + "\n");
//        }
//        fileWriter.flush();
//        fileWriter.close();
    }
}
