package com.bitauto.ep.fx.webapi.utils.web;

import java.io.File;
import java.io.FileOutputStream;

public class FileUploadUtil {

    /**
     * 上传文件
     * @param file 文件的二进制byte[]
     * @param filePath 文件上传的路径
     * @param fileName 上传文件名
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
