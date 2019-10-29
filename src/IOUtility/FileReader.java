package IOUtility;

import java.io.*;

public class FileReader {
    public static FileReader instance=null;
    public static FileReader getInstance(){
        return instance==null?(instance=new FileReader()):instance;
    }
    private FileReader(){}


    public String getStringFromFile(String path) throws IOException {
        String re="";
        File f=new File(path);
        FileInputStream fis=new FileInputStream(f);
        BufferedInputStream bis=new BufferedInputStream(fis);
        byte[] bs=new byte[(int)f.length()];
        bis.read(bs);
        re+=new String(bs,"UTF-8");


        return re;
    }

    //Test
    public static void main(String args[]){
        try {
            System.out.println(getInstance().getStringFromFile("F:\\数据分析\\jnlp\\5.txt"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
