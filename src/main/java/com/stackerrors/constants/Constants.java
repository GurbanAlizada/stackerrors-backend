package com.stackerrors.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {


    public static   String CLOUD_NAME;


    public static String API_KEY;


    public static String API_SECRET;


    @Value("${cloud-service.cloudName}")
    public void setCLOUD_NAME(String CLOUD_NAME) {
        this.CLOUD_NAME = CLOUD_NAME;
    }


    @Value("${cloud-service.apiKey}")
    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }


    @Value("${cloud-service.apiSecret}")
    public void setAPI_SECRET(String API_SECRET) {
        this.API_SECRET = API_SECRET;
    }



}
