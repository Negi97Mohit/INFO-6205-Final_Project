package edu.neu.coe.info6205.FinalProject.Utils;


import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class ChineseComparator implements Comparator<String> {
    Collator collator = Collator.getInstance(Locale.CHINA);

    public int compare(String s1, String s2) {
        return collator.compare(s1, s2);
    }

}