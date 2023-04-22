package com.stackerrors.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class AwsConfigure {

    @Value("${cloud.aws.accesskey}")
    public String S3_ACCESS_KEY ;

    @Value("${s3.region}")
    public  String REGION ;

    @Value("${cloud.aws.secretkey}")
    public  String S3_SECRET_KEY;




    @Bean
    public AmazonS3 s3Client() {
        System.out.println("S3_SECRET_LEY : " + S3_SECRET_KEY);
        AWSCredentials credentials = new BasicAWSCredentials(S3_ACCESS_KEY,S3_SECRET_KEY );
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(REGION).build();
    }





}
