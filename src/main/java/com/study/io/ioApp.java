package com.study.io;

import org.springframework.util.FileCopyUtils;

import java.io.*;

public class ioApp {

//https://blog.csdn.net/m0_50744075/article/details/125888541
//https://blog.csdn.net/yyds_liangshen/article/details/130495257

    public static void main( String[] args ) throws IOException {
        //InOrOutStreamOne();

        FileByteOperation();

    }

    private static void FileByteOperation() throws IOException {
        File fileIn=new File("E:\\inputFile\\yqs.PNG");
        byte bytes[] = FileCopyUtils.copyToByteArray(fileIn);

        File fileOut =new File("E:\\outputFile\\yqs.PNG");
        FileCopyUtils.copy(bytes,fileOut);
    }

    private static void InOrOutStreamOne() throws IOException {
        File fileIn=new File("E:\\inputFile\\yqs.PNG");
        InputStream in = new BufferedInputStream(new FileInputStream(fileIn)) ;
        byte bytes[] = FileCopyUtils.copyToByteArray(in);
        in.close();

        File fileOut =new File("E:\\outputFile\\yqs.PNG");
        OutputStream out = new BufferedOutputStream(new FileOutputStream(fileOut));
        out.write(bytes);
        out.flush();
        out.close();
    }
}
