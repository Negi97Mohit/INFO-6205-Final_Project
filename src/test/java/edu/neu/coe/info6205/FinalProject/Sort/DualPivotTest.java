package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;
import edu.neu.coe.info6205.FinalProject.RegexMatch;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DualPivotTest extends TestCase {

    public static String[] chiToEng;

    @Before
    public void setup() throws FileNotFoundException {
    }

    @Test
    public void testSort1() throws IOException {
        String[] word = { "阿安", "阿彬", "阿斌", "阿滨", "阿兵", "阿冰", "阿冰冰"};
        DualPivot dp = new DualPivot();
        String resource = "chinese_names.txt";
        String[] pin = ChineseToEnglish.generateList(resource);
        chiToEng = new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = RegexMatch.getPingYin(pin[i]);
        }
        int n = chiToEng.length;
        dp.sort(chiToEng);
        String[] res = new String[word.length];

        for (int i = 0; i < word.length; i++)
            res[i] = chiToEng[i];
        for(int i=0;i< word.length;i++)
            System.out.println(res[i] +" "+ RegexMatch.getPingYin(word[i]) + " " +word[i]);
        for (int i=0;i<word.length;i++){
            assertEquals( res[i], RegexMatch.getPingYin(word[i]));
        }


    }

}