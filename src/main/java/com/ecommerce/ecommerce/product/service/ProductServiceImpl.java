package com.ecommerce.ecommerce.product.service;

import com.ecommerce.ecommerce.common.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.product.entity.Product;
import com.ecommerce.ecommerce.product.repository.ProductRepository;
import com.ecommerce.ecommerce.security.JwtUtil;
import com.ecommerce.ecommerce.user.entity.User;
import com.ecommerce.ecommerce.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommerce.product.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product createProduct(Product product, String token) {
        logger.info("Creating product: {}", product.getName());
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        product.setUser(user);

        logger.info("Product created successfully with id: {}", product.getId());
        return productRepository.save(product);
    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct, String token) {
        String email = jwtUtil.extractUsername(token.substring(7));
        User loggedInUser = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        Product existing =  productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        if(!existing.getUser().getId().equals(loggedInUser.getId())) {
            throw new AccessDeniedException("You are not authorized to update this product!");
        }

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());

        return productRepository.save(existing);
    }



    @Override
    public void deleteProduct(Long id, String token) {
        logger.error("Product not found with id: {}", id);
        String email = jwtUtil.extractUsername(token.substring(7));
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Product existing =  productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        logger.info("Attempting to delete product with id: {}", id);
        if(!productRepository.existsById(id)) {

            throw new ResourceNotFoundException("Product not found with ID: " + id);
        }
        productRepository.delete(existing);
        logger.info("Product deleted successfully with id: {}", id);

    }


}
