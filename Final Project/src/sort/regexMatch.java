package sort;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.lang.Object;

import org.apache.commons.lang3.ArrayUtils;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
/**
 *
 * @author MNegi
 */
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexMatch {
    public boolean checkRex(char input) {

        // String to be scanned to find the pattern.

        //char l = '大';
        CharSequence line = String.valueOf(input);
        //CharSequence line = ",";
        //String pattern = "(.*)(\\d+)(.*)";
        //String pattern = "[^a-zA-Z0-9\\s]";
        String pattern = "\\p{L}";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    //method to check if it is a english alphanumeric character. Chinese characters are not treated as
    //such in an regex like this "[^a-zA-Z0-9\\s]"
    public boolean checkAlpha(char input) {
        CharSequence line = String.valueOf(input);
        //CharSequence line = ",";
        //String pattern = "(.*)(\\d+)(.*)";
        String pattern = "[a-zA-Z0-9\\s]";
        //String pattern = "\\p{L}";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }


    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (java.lang.Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                                input[i], format);
                        output += temp[0];
                    } else
                        output += java.lang.Character.toString(input[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output;
    }
}