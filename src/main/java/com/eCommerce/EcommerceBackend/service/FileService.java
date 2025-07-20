package com.eCommerce.EcommerceBackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadImage(String path, MultipartFile fileImage) throws IOException;
}
