package com.example.minio;

import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.minio.User;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/users")

public class UserController {
    private UserRepository userRepository;
    private MinioService minioService;

    public UserController(UserRepository userRepository, MinioService minioService) {
        this.userRepository = userRepository;
        this.minioService = minioService;
    }

    @PostMapping("/add")
    //@RequestBody ile form-data Göndermek: @RequestBody, genellikle application/json, application/xml gibi formatlarda gelen verilerle çalışır. Eğer form-data formatında veri gönderiyorsanız, bunu @RequestBody ile almak doğrudan mümkün değildir.
    //@RequestParam ve @ModelAttribute Kullanımı:
    //form-data(Muktipartile  var cunku) verilerini almak için genellikle @RequestParam veya @ModelAttribute kullanılır.
    public String registerUser(@RequestParam String tcKimlikNo,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam int age,
                               @RequestParam MultipartFile profilePhoto) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {

        // MultipartFile'dan InputStream elde etme
        InputStream photoStream = profilePhoto.getInputStream();
        // MinIO'ya fotoğrafı yükle
        String profilePhotoUrl = minioService.uploadPhoto(photoStream, tcKimlikNo);


        User user = new User();
        user.setTcKimlikNo(tcKimlikNo);
        user.setName(firstName);
        user.setSurname(lastName);
        user.setAge(age);
        user.setProfilePhoto(profilePhotoUrl);

        userRepository.save(user);

        return "Kayıt edildi";

    }
}