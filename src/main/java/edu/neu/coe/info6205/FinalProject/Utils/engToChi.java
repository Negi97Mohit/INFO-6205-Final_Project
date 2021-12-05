package edu.neu.coe.info6205.FinalProject.Utils;

public class engToChi {
    static String last;
    public static void main(String[] args) {

    }
    public static String[] swapper(String[] original,String[] beSort,String[] afSort){
        String[] result=new String[original.length];
        binarySearch bs=new binarySearch();
        int[] poss=new int[original.length];
        for(int i=0;i<original.length;i++) {
            String find = beSort[i];
            int pos = bs.binarySearch(afSort, find);
            result[pos]=original[i];
        }
        for (int i=0;i<original.length;i++){
            if(result[i]==null){
                result[i]=last;
            }
            else{
                last=result[i];
            }
        }
        return result;
    }
}

