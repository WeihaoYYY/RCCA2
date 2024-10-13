package com.example.rcca2.tools;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.log4j.Log4j2;
//import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Log4j2
public class S3Utils {

    // S3访问密钥 / S3 Access Key
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKeyId;

    // S3加密访问密钥 / S3 Secret Access Key
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretAccessKey;

    // 服务端点 / Endpoint
    @Value("${cloud.aws.s3.endpoint}")
    private String endPoint;

    // 桶名 / Bucket Name
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3Utils() {
        // 无参构造函数，使用注入的配置
    }

    /**
     * 创建一个对象存储桶客户端连接 / Create an S3 Client Connection
     *
     * @return AmazonS3 客户端实例 / AmazonS3 Client Instance
     */
    public AmazonS3 createS3Client() {
        System.out.println("accessKeyId: " + accessKeyId);
        System.out.println("secretAccessKey: " + secretAccessKey);

        // 创建s3 client / Create S3 Client
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // 添加客户端最大连接数 / Set Maximum Client Connections
        clientConfiguration.setMaxConnections(1000);
        AWSCredentials awsCredentials = new BasicAWSCredentials(this.accessKeyId, this.secretAccessKey);
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.endPoint, Regions.AP_SOUTHEAST_2.getName())) // 使用悉尼地区
                .withClientConfiguration(clientConfiguration)
                .withPathStyleAccessEnabled(true)
                .build();
        return client;
    }

    /**
     * 上传文件到对象存储桶 / Upload File to S3 Bucket
     *
     * @param file 要上传的文件 / File to Upload
     * @param fileKey 文件在存储桶中的键 / File Key in the Bucket
     * @return Boolean 上传是否成功 / Upload Success
     */
    public boolean uploadFile(File file, String fileKey) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 上传文件 / Upload File
            s3Client.putObject(bucketName, fileKey, file);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * 上传文件到对象存储桶 - 文件流方式 / Upload File to S3 Bucket - Stream Method
     *
     * @param inputStream 要上传的文件流 / InputStream to Upload
     * @param fileKey 文件在存储桶中的键 / File Key in the Bucket
     * @return boolean 上传是否成功 / Upload Success
     */
    public boolean uploadFileByStream(InputStream inputStream, String fileKey) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 上传文件 / Upload File
            s3Client.putObject(bucketName, fileKey, inputStream, new ObjectMetadata());
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * 下载文件 / Download File
     *
     * @param fileKey 文件在存储桶中的键 / File Key in the Bucket
     * @return InputStream 文件输入流 / File InputStream
     */
    public InputStream downloadFile(String fileKey) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 下载文件 / Download File
            S3Object object = s3Client.getObject(bucketName, fileKey);
            return object.getObjectContent();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 移动文件（删除原文件） / Move File (Delete Original File)
     *
     * @param oldFileKey 原文件在存储桶中的键 / Original File Key in the Bucket
     * @param newFileKey 新文件在存储桶中的键 / New File Key in the Bucket
     * @return boolean 移动是否成功 / Move Success
     */
    public boolean mvFile(String oldFileKey, String newFileKey) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 复制文件 / Copy File
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketName, oldFileKey, bucketName, newFileKey);
            s3Client.copyObject(copyObjectRequest);
            // 删除原文件 / Delete Original File
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, oldFileKey));
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * 删除文件 / Delete File
     *
     * @param fileKey 文件在存储桶中的键 / File Key in the Bucket
     * @return boolean 删除是否成功 / Delete Success
     */
    public boolean deleteFile(String fileKey) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 删除文件 / Delete File
            s3Client.deleteObject(bucketName, fileKey);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * 删除文件列表 / Delete Multiple Files
     *
     * @param fileList 要删除的文件键列表 / List of File Keys to Delete
     * @return boolean 删除是否成功 / Delete Success
     */
    public boolean deleteFiles(String[] fileList) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 删除多个文件 / Delete Multiple Files
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withKeys(fileList);
            s3Client.deleteObjects(deleteObjectsRequest);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        return true;
    }

    /**
     * 查找桶内文件是否存在 / Check if File Exists in Bucket
     *
     * @param fileKey 文件在存储桶中的键 / File Key in the Bucket
     * @return boolean 是否存在 / Exists
     */
    public boolean exist(String fileKey) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            ListObjectsV2Result listObjectsV2Result = s3Client.listObjectsV2(bucketName, fileKey);
            System.out.println(bucketName);
            System.out.println(fileKey);
            return listObjectsV2Result.getKeyCount() > 0;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    /**
     * 根据prefix获取文件列表 / Get File List by Prefix
     * 注意：若不进行do-while，只进行一次s3Client.listObjectsV2(req)，则最多展示1000条 / Note: Without do-while, only 1000 entries will be returned at most
     *
     * @param prefix 文件前缀 / File Prefix
     * @return List<S3ObjectSummary> 文件列表 / File List
     */
    public List<S3ObjectSummary> searchPathFileByPrefix(String prefix) {
        ArrayList<S3ObjectSummary> returnList = new ArrayList<>();
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 按prefix获取文件列表 / Get File List by Prefix
            ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);
            ListObjectsV2Result result;
            if (prefix != null && !prefix.isEmpty()) {
                req = req.withPrefix(prefix);
            }
            do {
                result = s3Client.listObjectsV2(req);
                returnList.addAll(result.getObjectSummaries());
                String token = result.getNextContinuationToken();
                req.setContinuationToken(token);
            } while (result.isTruncated());
            return returnList;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 生成文件的预签名URL / Generate Pre-signed URL for File
     *
     * @param fileKey 文件在存储桶中的键 / File Key in the Bucket
     * @return String 预签名URL / Pre-signed URL
     */
    public String generalUrl(String fileKey) {
        try {
            // 获取客户端连接 / Get Client Connection
            AmazonS3 s3Client = this.createS3Client();
            // 设置预签名失效时间 - 最多七天 / Set Expiration Time - Max 7 Days
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            Date date = calendar.getTime();
            // 生成预签名链接 / Generate Pre-signed URL
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileKey)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(date);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
}
