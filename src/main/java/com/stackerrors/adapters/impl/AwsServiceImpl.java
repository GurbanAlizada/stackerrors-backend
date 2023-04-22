package com.stackerrors.adapters.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.stackerrors.adapters.inter.CloudServiceInter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("awsServiceImpl")
@Slf4j
public class AwsServiceImpl implements CloudServiceInter {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucketName}")
    public  String S3_BUCKET_NAME;

    @Value("${s3.bucket.base.url}")
    public  String S3_BASE_URL;

    public AwsServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    @Override
    public Map<String , String> uploadImage(MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        File fileObj = convert(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(S3_BUCKET_NAME, fileName, fileObj));
        fileObj.delete();
        System.out.println(fileName);
        result.put("secure_url" , S3_BASE_URL + fileName );
        result.put("public_id" , fileName);
        return result;
    }



    @Override
    public void deleteImage(String imageUrl) {
        int baseUrlSize = S3_BASE_URL.length();
        String fileName = imageUrl.substring(baseUrlSize);
        System.out.println(fileName);
        amazonS3.deleteObject(S3_BUCKET_NAME, fileName);
        log.info(fileName + " silindi");
    }




    private File convert(final MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
