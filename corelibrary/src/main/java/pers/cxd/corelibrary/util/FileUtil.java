package pers.cxd.corelibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

}
