package com.hellobike.test.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class FileOperation {

    public static String fileRead(String filePath) {
        String content = "";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);

            //自定义缓冲区
            byte[] buffer = new byte[10240];
            int flag = 0;
            while ((flag = bis.read(buffer)) != -1) {
                content += new String(buffer, 0, flag);
            }
//            System.out.println(content);
            //关闭的时候只需要关闭最外层的流就行了
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
