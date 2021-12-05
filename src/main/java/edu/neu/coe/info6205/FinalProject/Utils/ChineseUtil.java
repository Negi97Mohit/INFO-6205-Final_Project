package edu.neu.coe.info6205.FinalProject.Utils;


import java.text.CollationKey;
import java.text.Collator;
import java.util.Locale;

public class ChineseUtil {
    private static final Collator collator = Collator.getInstance(Locale.CHINA);
    public static byte[] toByteArray(String string){
        return collator.getCollationKey(string).toByteArray();
    }

    public static CollationKey getCollatorKey(String string){
        return collator.getCollationKey(string);
    }

//    public static void main(String[] args) {
//        CollationKey a = getCollatorKey("刘持平");
//        System.out.println(a);
//    }
}