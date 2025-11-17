package com.ecommerce.ecommerce.order.service;

import com.ecommerce.ecommerce.common.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.order.entity.Order;
import com.ecommerce.ecommerce.order.repository.OrderRepository;
import com.ecommerce.ecommerce.product.entity.Product;
import com.ecommerce.ecommerce.product.repository.ProductRepository;
import com.ecommerce.ecommerce.user.entity.User;
import com.ecommerce.ecommerce.user.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.lang.Long.sum;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);



    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public List<Order> getOrderByuser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        return orderRepository.findByUser(user);
    }

    public Order placeOrder(Long userId, List<Long> productIds, String shippingAddress) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found" + userId));
        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found for this ID");
        }
        double totalAmount = products.stream().mapToDouble(Product::getPrice).sum();
        Order order = new Order();
        order.setUser(user);
        order.setProducts(products);
        order.setTotalAmount(totalAmount);
        order.setShippingAddress(shippingAddress);
        Order savedOrder=  orderRepository.save(order);
        String message = String.format("🛒 New order placed! ID: %d | User: %s | Total: %.2f | Products: %d",
        savedOrder.getId(), user.getName(), totalAmount, products.size());

        kafkaTemplate.send("order-template", message);
        return savedOrder;

    }

        

}
