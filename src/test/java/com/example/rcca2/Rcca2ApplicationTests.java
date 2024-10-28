package com.example.rcca2;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.example.rcca2.Entities.Item;
import com.example.rcca2.Services.ItemService;
import com.example.rcca2.tools.S3Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;



import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class Rcca2ApplicationTests {

    @Autowired
    private ItemService itemService;

//    @Test
//    void s3() {
//        String fileKey = "r2/images/items/bmw-i8.jpg";
//        S3Utils s3 = new S3Utils();
//
//
//        System.out.println(s3.exist(fileKey));
//
//    }
//
//    @Test
//    void approval(){
//        itemService.approval(24L, 2);
//    }
//
//    @Test
//    void connectionTesting(){
//        S3Utils s3Utils = new S3Utils();
//
//        // 调用 createS3Client 方法
//        AmazonS3 s3Client = s3Utils.createS3Client();
//
//        // 检查 S3 客户端实例是否成功创建
//        System.out.println(s3Client);
//
//        // 测试列出存储桶（可选）
//        try {
//            List<Bucket> buckets = s3Client.listBuckets();
//            assertNotNull(buckets);
//            System.out.println("存储桶列表：");
//            buckets.forEach(bucket -> System.out.println(bucket.getName()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("无法访问 S3，请检查密钥和权限配置。");
//        }
//    }
}


