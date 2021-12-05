package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;
import edu.neu.coe.info6205.FinalProject.Utils.engToChi;
import edu.neu.coe.info6205.FinalProject.regexMatch;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;

import java.io.FileNotFoundException;

public class radixSortMSDTest extends TestCase {
    public static String[] chiToEng;
    @Before
    public void setup() throws FileNotFoundException {
    }

    @Test
    public void testSort() throws FileNotFoundException {
        String[] word = { "阿安", "阿彬", "阿斌", "阿滨", "阿兵", "阿冰", "阿冰冰"};
        radixSortMSD rMSD = new radixSortMSD();
        String resource="chinese_names.txt";
        String[] pin= ChineseToEnglish.generateList(resource);
        chiToEng=new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }
        int n = chiToEng.length;
        rMSD.sort(chiToEng);
        String[] res=new String[word.length];
        for(int i=0;i<word.length;i++)
            res[i]=chiToEng[i];

        String[] beSort=new String[chiToEng.length];
        for(int i=0;i<n;i++)
            beSort[i]=chiToEng[i];

        for(int i=0;i< word.length;i++)
            System.out.println(res[i] +" "+ regexMatch.getPingYin(word[i]) + " " +word[i]);
        for (int i=0;i<word.length;i++){
                assertEquals( res[i], regexMatch.getPingYin(word[i]));
        }

    }
}