package com.stackerrors.adapters.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.stackerrors.adapters.inter.CloudServiceInter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service("awsServiceImpl")
@RequiredArgsConstructor
@Slf4j
public class AwsServiceImpl implements CloudServiceInter {

    private final AmazonS3 amazonS3;

    @Value("${s3.bucketName}")
    private String bucketName;
    @Value("${s3.bucket.base.url}")
    private String baseUrl;


    @Override
    public Map<String , String> uploadImage(MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        File fileObj = convert(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        System.out.println(fileName);
        result.put("secure_url" , baseUrl + fileName );
        result.put("public_id" , fileName);
        return result;
    }



    @Override
    public void deleteImage(String imageUrl) {
        int baseUrlSize = baseUrl.length();
        String fileName = imageUrl.substring(baseUrlSize);
        System.out.println(fileName);
        amazonS3.deleteObject(bucketName, fileName);
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
