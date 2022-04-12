package com.hellobike.test.support;

import java.io.FileWriter;
import java.io.IOException;

public class SaveAsFile {
    public static void saveFile(String context, String filePath) {
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(filePath);
            fwriter.write(context);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
