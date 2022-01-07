package pers.cxd.corelibrary.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Consumer;

public class IOUtil {

    public static void readLine(BufferedReader reader, Consumer<String> consumer) throws IOException {
        String line;
        while ((line = reader.readLine()) != null){
            consumer.accept(line);
        }
    }

}
