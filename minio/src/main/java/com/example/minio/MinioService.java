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

    private static final String MINIO_URL = "";
    private static final String ACCESS_KEY = "";
    private static final String SECRET_KEY = "";
    private static final String BUCKET_NAME = "";

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
        String objectName =  tcKlimlikNo + ".jpg"; // bucket altında  altına tckimlikNo.jpg olarak kayit edilecek

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(photo, photo.available(), -1) // InputStream'den fotoğrafı yükleme
                        .contentType("image/jpg")
                        .build()
        );

        String permanentUrl = MINIO_URL + "/" + BUCKET_NAME + "/" + objectName;
        return permanentUrl;//dbye kaydedilecek veri ve bu url ile göruntuye ulasacagiz
    }


}