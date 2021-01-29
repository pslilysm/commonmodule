package pers.cxd.corelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;

public class FileUtil {

    public static String readFileString(File file){
        try(InputStream is = new FileInputStream(file)){
            byte[] data = new byte[is.available()];
            is.read(data);
            return new String(data);
        }catch (IOException ex){
            return null;
        }
    }

    public static int getFileLineNumber(File file){
        try (LineNumberReader reader = new LineNumberReader(new FileReader(file))){
            reader.skip(Integer.MAX_VALUE);
            return reader.getLineNumber();
        } catch (IOException e) {
            return 0;
        }
    }

}
