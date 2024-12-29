package com.example.minio;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    private static final String MINIO_URL = "MINIO_URL";
    private static final String ACCESS_KEY = "ACCESS_KEY";
    private static final String SECRET_KEY = "SECRET_KEY";
    private static final String BUCKET_NAME = "BUCKET_NAME";

    private MinioClient minioClient;
    private String bucketName;

    public MinioService() {
        this.minioClient = MinioClient.builder()
                .endpoint(MINIO_URL)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
        this.bucketName = BUCKET_NAME;
    }

    public String uploadPhoto(InputStream photo, String tcKlimlikNo) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        String objectName = "profile_photos/" + tcKlimlikNo + ".jpg"; // bucket altında profile_photoss/ altına tckimlikNo.jpg olarak kayit edilecek

       ObjectWriteResponse response=  minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(photo, photo.available(), -1) // InputStream'den fotoğrafı yükleme
                        .contentType("image/jpg")
                        .build()
        );

return response.toString(); //dbye kaydedilecek veri
    }


}