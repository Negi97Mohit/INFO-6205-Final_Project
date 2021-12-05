package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.ChineseToEnglish;
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
        String[] word={"阿安","阿安","阿彬","阿彬","阿兵","阿兵","阿冰冰`","阿婵","阿超"};
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
        boolean testStatus=false;
        for (int i=0;i<word.length;i++){
            if (res[i].equals(word[i])==false)
                testStatus=false;
            else
                testStatus=true;
        }

    }
}