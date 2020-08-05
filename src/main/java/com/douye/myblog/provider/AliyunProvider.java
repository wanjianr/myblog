package com.douye.myblog.provider;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.douye.myblog.exception.CustomizeErrorCode;
import com.douye.myblog.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class AliyunProvider {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName ;

    public String upload(InputStream fileStream, String fileName) {
        String objectName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            objectName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        ossClient.putObject(bucketName, objectName, fileStream);
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 24 * 365 * 1000);
        URL url = ossClient.generatePresignedUrl(bucketName,objectName, expiration);
        // 关闭OSSClient。
        ossClient.shutdown();
        return String.valueOf(url);
    }
}
