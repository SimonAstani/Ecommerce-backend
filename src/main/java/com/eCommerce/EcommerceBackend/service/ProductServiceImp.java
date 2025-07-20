package com.eCommerce.EcommerceBackend.service;

import com.eCommerce.EcommerceBackend.exception.ResourseNotFoundException;
import com.eCommerce.EcommerceBackend.model.Category;
import com.eCommerce.EcommerceBackend.model.Product;
import com.eCommerce.EcommerceBackend.payload.ProductDTO;
import com.eCommerce.EcommerceBackend.payload.ProductResponse;
import com.eCommerce.EcommerceBackend.repositories.CategoryRepository;
import com.eCommerce.EcommerceBackend.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;


    @Value("${project.images}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, Product productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                    new ResourseNotFoundException("Category", "cattegoryId", categoryId)
                );

        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default.png");
        product.setCategory(category);

        double specialPrice = product.getPrice() - (product.getDiscount() * 0.01 * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);


        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getAllProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourseNotFoundException("Category", "CategoryId", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getAllProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%");

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        //get existing proudct from DB
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourseNotFoundException("Product", "productId", productId));

        Product product = modelMapper.map(productDTO, Product.class);
        // update the product infor with one with request body
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());
        //saved to DB
        Product savedProduct = productRepository.save(productFromDb);


        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourseNotFoundException("Proudct", "Product Id", productId));
        productRepository.delete(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //get product from db
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(()-> new ResourseNotFoundException("Product", "ProductID",productId));
        //upload image to server or to folder
        String fileName = fileService.uploadImage(path, image);
        //get filename of image
        //update filename to product
        productFromDb.setImage(fileName);

        //save updated product
        Product updatedProduct = productRepository.save(productFromDb);
        //return dto after mapping
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }
}
