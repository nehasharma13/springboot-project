package com.ecommerce.ecommerce.product.service;

import com.ecommerce.ecommerce.product.entity.Product;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

public interface ProductService {



    public Product createProduct(Product product, String token);
    public Product getProductById(Long id);
    public List<Product> getAllProducts();
    public Product updateProduct(Long id, Product product, String token);
    public void deleteProduct(Long id, String token);
}
