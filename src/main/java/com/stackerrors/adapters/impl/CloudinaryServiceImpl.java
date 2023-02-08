package com.stackerrors.adapters.impl;

import com.stackerrors.adapters.inter.CloudServiceInter;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service("cloudinaryServiceImpl")
public class CloudinaryServiceImpl implements CloudServiceInter {

    private Cloudinary cloudinary;
    @Value("${cloud-service.cloudName}")
    private String cloudName;
    @Value("${cloud-service.apiKey}")
    private String apiKey;
    @Value("${cloud-service.apiSecret}")
    private String apiSecret;

    public CloudinaryServiceImpl() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name", "alizada" );
        valuesMap.put("api_key", "231755285583346" );
        valuesMap.put("api_secret","P2WIwBBTmrFrWK3FxfBT1gkfugQ" );
        cloudinary = new Cloudinary(valuesMap);
    }


    @Override
    public Map<String,String> uploadImage(MultipartFile multipartFile) {

        Map<String, String> result = null ;
        File file ;

        try {
            file = convert(multipartFile);
            result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }




    @Override
    public void deleteImage(String publishId) throws IOException {
        cloudinary.uploader().destroy( publishId , ObjectUtils.emptyMap());
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
