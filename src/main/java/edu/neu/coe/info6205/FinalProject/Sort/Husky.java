
package edu.neu.coe.info6205.FinalProject.Sort;

import edu.neu.coe.info6205.FinalProject.Utils.ChineseComparator;
import edu.neu.coe.info6205.FinalProject.regexMatch;
import edu.neu.coe.info6205.FinalProject.toEng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;


public class Husky extends Sort {
    public int byteAt(byte[] s, int d)
    {
        if (d < s.length) return s[d]&0xFF; // when doing msd the right part of array seems not important ?
        else return -1;
    }
    public void sort(String[] a) throws IOException {
        Collator co = Collator.getInstance(Locale.CHINA);
        byte[][] aux = new byte[a.length][];

        byte[][] test=new byte[a.length][];
        for(int i=0;i< a.length;i++){

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write( co.getCollationKey(a[i]).toByteArray());
            outputStream.write( a[i].getBytes(StandardCharsets.UTF_8) );
            test [i] = outputStream.toByteArray( );

        }
        sort(test, aux, 0, a.length -1, 0);
        for(int j=0;j<test.length;j++){

            a[j]=new String(getByteString(test[j]));
        }
    }
    public byte[] getByteString(byte [] a){ // since the key byte and word byte are different, need to find better way to do this
        if(a.length<20&&a.length>=15)
        {
            //System.out.println("less than 20");
            return Arrays.copyOfRange(a,9,a.length);
        }else if(a.length<15){

            return Arrays.copyOfRange(a,8,a.length);
        }else{
            return Arrays.copyOfRange(a,11,a.length);
        }

    }

    private void sort(byte[][] a, byte[][] aux, int lo, int hi, int d)
    {
        int R = 256;
        if (hi <= lo) return;
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++)
            count[byteAt(a[i], d) + 2]++;
        for (int r = 0; r < R+1; r++)
            count[r+1] += count[r];
        for (int i = lo; i <= hi; i++)
            aux[count[byteAt(a[i], d) + 1]++] = a[i];
        if (hi + 1 - lo >= 0) System.arraycopy(aux, 0, a, lo, hi + 1 - lo);
        for (int r = 0; r < R; r++)
            sort(a, aux, lo + count[r], lo + count[r+1] - 1, d+1);
    }

    public static void main(String[] args) throws IOException {
        ChineseComparator y=new ChineseComparator();
        String resource="chinese_names.txt";
        String[] pin= toEng.generateList(resource);
        String[] chiToEng=new String[pin.length];
        for (int i = 0; i < pin.length; i++) {
            chiToEng[i] = regexMatch.getPingYin(pin[i]);
        }
        int n = chiToEng.length;
        Husky e=new Husky();
        e.sort(chiToEng);
        Arrays.sort(pin,(x1, x2)-> y.compare(x1,x2));
        double correct=0;
        System.out.println("Husky Sort Performed");
    }
}