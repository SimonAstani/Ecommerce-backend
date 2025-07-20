package com.eCommerce.EcommerceBackend.service;

import com.eCommerce.EcommerceBackend.model.Product;
import com.eCommerce.EcommerceBackend.payload.ProductDTO;
import com.eCommerce.EcommerceBackend.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product productDTO);

    ProductResponse getAllProducts();

    ProductResponse getAllProductsByCategory(Long categoryId);

    ProductResponse getAllProductsByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
