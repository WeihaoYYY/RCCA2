package com.example.rcca2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Rcca2ApplicationTests {


    @Autowired
    private DataSource dataSource;

    @Test
    public void testDatabaseConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            // Assert that connection is not null
            assertNotNull(connection, "Connection should not be null");

            // Assert that connection is valid
            assertTrue(connection.isValid(2), "Connection should be valid within 2 seconds");

            // Additional: Check database metadata (for example, database name)
            String databaseProductName = connection.getMetaData().getDatabaseProductName();
            System.out.println("Connected to: " + databaseProductName);
            assertNotNull(databaseProductName, "Database product name should not be null");
        }
    }

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


