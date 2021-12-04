package edu.neu.coe.info6205.FinalProject.Utils;

public class ByteNode {
    private String ChineseChar;
    private byte[] byteArray;
    public ByteNode(String word) {
        ChineseChar=word;
        byteArray = ChineseUtil.toByteArray(word);
    }
    public ByteNode(){
        ChineseChar=null;
        byteArray =null;
    }
    public void setNode(String word){
        ChineseChar=word;
        byteArray = ChineseUtil.toByteArray(word);
    }
    public String getChineseChar(){
        return ChineseChar;
    }
    public byte[] getByteArray(){
        return byteArray;
    }
}