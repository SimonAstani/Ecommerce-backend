package com.eCommerce.EcommerceBackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile fileImage) throws IOException {
        //files name of current/orginial file
        String orginalFilenName = fileImage.getOriginalFilename();

        //rename the file uniqely: randmom unique id
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(orginalFilenName.substring(orginalFilenName.lastIndexOf(".")));
        String filePath = path + File.pathSeparator + fileName;

        // Check if path exist or not
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        //upload to server
        Files.copy(fileImage.getInputStream(), Paths.get(filePath));

        //return file
        return fileName;
    }

}
