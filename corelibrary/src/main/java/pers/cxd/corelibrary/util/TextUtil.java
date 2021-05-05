package pers.cxd.corelibrary.util;

import android.text.TextUtils;

public class TextUtil {

    public static String cleanWhiteSpace(String str){
        return str.replaceAll("\\s+", "");
    }

    public static String cleanWrap(String str){
        return str.replaceAll("[\\s*\t\n\r]", "");
    }

    public static String nullIfEmpty(String s1){
        return TextUtils.isEmpty(s1) ? null : s1;
    }

}
