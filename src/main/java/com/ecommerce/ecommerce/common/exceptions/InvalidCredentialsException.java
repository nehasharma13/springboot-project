package com.ecommerce.ecommerce.common.exceptions;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
    super(message);
    }
}
