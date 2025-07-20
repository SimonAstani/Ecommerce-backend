package com.eCommerce.EcommerceBackend.Controller;

import com.eCommerce.EcommerceBackend.model.Product;
import com.eCommerce.EcommerceBackend.payload.ProductDTO;
import com.eCommerce.EcommerceBackend.payload.ProductResponse;
import com.eCommerce.EcommerceBackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product productDTO,
                                                 @PathVariable Long categoryId){
        ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        ProductResponse productResponseByCategory = productService.getAllProductsByCategory(categoryId);
        return new ResponseEntity<>(productResponseByCategory, HttpStatus.OK);

    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
        ProductResponse productResponse = productService.getAllProductsByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long productId){
        ProductDTO updatedproductDTO = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedproductDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deleteproduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deleteproduct, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }



}
