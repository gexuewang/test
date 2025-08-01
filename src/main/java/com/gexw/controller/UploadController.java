package com.gexw.controller;

import com.gexw.client.FastDFSClient;
import com.gexw.context.CachePrefix;
import com.gexw.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class UploadController {

//    @Value("gexw.g1.fastServer")
//    private String fastServer;

    @Autowired
    private CachePrefix cachePrefix;


    @PostMapping("/upload")
    public Result upload(MultipartFile image) {
        System.out.println(image);
        FastDFSClient fastDFSClient = null;
        try {
            String fileName = image.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));

            fastDFSClient = new FastDFSClient("fdfs_client.conf");
            String fileId = fastDFSClient.uploadFile(image.getBytes(), suffix);
            String imageUrl = cachePrefix.getFastServer() + fileId;

            log.info("文件上传的地址为{}",imageUrl);
            return Result.success(imageUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
