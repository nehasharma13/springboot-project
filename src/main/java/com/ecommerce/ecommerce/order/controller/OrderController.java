package com.ecommerce.ecommerce.order.controller;

import com.ecommerce.ecommerce.order.entity.Order;
import com.ecommerce.ecommerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")
public class OrderController {


    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/place")
    @Operation(
            summary = "Place a new order",
            description = "Creates a new order for a given user with a list of product IDs and shipping address",
            security = @SecurityRequirement(name = "bearerAuth") // indicates JWT is required
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid/missing JWT"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<Order> placeOrder(@Parameter(description = "ID of the user placing the order", required = true) @RequestParam Long userId,
                                            @Parameter(description = "List of product IDs to include in the order", required = true) @RequestParam List<Long> productIds,
                                            @Parameter(description = "Shipping address for the order", required = true) @RequestParam String shippingAddress) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(userId, productIds, shippingAddress));


    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get all orders for a specific user",
            description = "Returns all orders placed by the user with the given ID",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid/missing JWT"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public List<Order> getOrdersByUser(@Parameter(description = "ID of the user to fetch orders for", required = true) @PathVariable Long userId) {
        return orderService.getOrderByuser(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    @Operation(
            summary = "Get all orders",
            description = "Returns all orders in the system. Admin only.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid/missing JWT"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }


}
