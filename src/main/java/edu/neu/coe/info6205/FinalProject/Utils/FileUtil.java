//package edu.neu.coe.info6205.FinalProject;
//
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.List;
//import java.util.Vector;
//
//public class FileUtil {
//    public static List<String> readFile(String fileName) throws IOException {
//        List<String> container=new Vector<>();
//
//        try (BufferedReader read= new BufferedReader(new FileReader(fileName))){
//            String inputLine=null;
//            while((inputLine=read.readLine())!=null) {
//                container.add(inputLine);
//            }
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return container;
//
//    }
//
//    public static String[] readFileInRange(String fileName,int range) throws IOException {
//        String[] container=new String[range];
//
//        try (BufferedReader read= new BufferedReader(new FileReader(fileName))){
//            String inputLine=null;
//            for(int i=0;i<range;i++){
//                if((inputLine=read.readLine())!=null){
//                    container[i]=inputLine;
//                }
//            }
//        } catch (FileNotFoundException e) {
//
//            e.printStackTrace();
//        }
//        return container;
//
//    }
//    public static WordNode[] readFileInRangeNode(String fileName,int range) throws IOException {
//        WordNode[] container=new WordNode[range];
//
//        try (BufferedReader read= new BufferedReader(new FileReader(fileName))){
//            String inputLine=null;
//            for(int i=0;i<range;i++){
//                if((inputLine=read.readLine())!=null){
//                    container[i]=new WordNode(inputLine);
//                }
//            }
//        } catch (FileNotFoundException | BadHanyuPinyinOutputFormatCombination e) {
//
//            e.printStackTrace();
//        }
//        return container;
//
//    }
//
//    public static ByteNode[] readFileInRangeByteNode(String fileName, int range) throws IOException {
//        ByteNode[] container=new ByteNode[range];
//
//        BufferedReader read= new BufferedReader(new FileReader(fileName));
//        String inputLine=null;
//        for(int i=0;i<range;i++){
//            if((inputLine=read.readLine())!=null){
//                container[i]=new ByteNode(inputLine);
//            }
//        }
//        return container;
//
//    }
//
//}