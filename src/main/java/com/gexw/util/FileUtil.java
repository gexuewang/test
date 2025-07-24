package com.gexw.util;



import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class FileUtil {
    /**
     * 功能描述：文件重命名
     * 方法参数：原始文件名
     * 返回值：
     * 作者：zz
     * 时间：2025/6/16 11:16
     */
    public static String reFileName(String fileName) {
        //使用UUID
        String uuidName = UUID.randomUUID().toString().replaceAll("-", "");
        //扩展名
        String suf = fileName.split("\\.")[1];
        return getDatePath() + uuidName + "." + suf;
    }

    /**
     * 功能描述：返回日期的路径
     * 方法参数：
     * 返回值：
     * 作者：zz
     * 时间：2025/6/16 11:26
     */
    public static String getDatePath() {
        return LocalDate.now().toString().replaceAll("-", "");
    }

    /**
     * 功能描述：文件上传的方法，不包含自动生成文件目录
     * 方法参数：MultipartFile myfile, HttpServletRequest request
     * 返回值：文件上传成功后的路径
     * 作者：zz
     * 时间：2025/6/16 11:30
     */
    public static String transferTo(MultipartFile myfile, HttpServletRequest request) throws IOException {
        //把文件上传到服务器的指定位置
        String realPath = request.getServletContext().getRealPath("");
        System.out.println("realPath = " + realPath);
        File file = new File(realPath + "/upload");
        if (!file.exists()) {
            file.mkdirs();
        }
        String reFileName = reFileName(myfile.getOriginalFilename());
        File file1 = new File(realPath + "/upload/" + reFileName);
        //把文件上传到服务器的指定位置
        myfile.transferTo(file1);
        return reFileName;
    }

    /**
     * 功能描述：文件上传的方法，包含自动生成文件目录
     * 方法参数：MultipartFile myfile, HttpServletRequest request
     * 返回值：文件上传成功后的路径
     * 作者：zz
     * 时间：2025/6/16 11:30
     */
    public static String transferToDate(MultipartFile myfile, HttpServletRequest request) throws IOException {
        // 新保存路径，保存到C:/images/下
        String path = "C:/images/" + getDatePath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String reFileName = reFileName(myfile.getOriginalFilename());
        File file1 = new File(path + "/" + reFileName);
        myfile.transferTo(file1);
        return getDatePath() + "/" + reFileName;
    }

    /**
     * 功能描述：文件上传的方法，包含自动生成文件目录
     * 方法参数：MultipartFile myfile, HttpServletRequest request
     * 返回值：文件上传成功后的路径
     * 作者：zz
     * 时间：2025/6/16 11:30
     */
    public static String transferToDateFile(MultipartFile myfile, HttpServletRequest request) throws IOException {
        //把文件上传到服务器的指定位置
        String path = "D:\\images\\" + getDatePath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //重命名
        String reFileName = reFileName(myfile.getOriginalFilename());
        File file1 = new File(path + "/" + reFileName);
        //把文件上传到服务器的指定位置
        myfile.transferTo(file1);
        return getDatePath() + "/" + reFileName;
    }

}
