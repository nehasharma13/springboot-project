package com.ecommerce.ecommerce.product.repository;

import com.ecommerce.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
