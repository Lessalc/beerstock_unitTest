package com.dio.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Exception {

    public BeerNotFoundException(String name) {
        super("Beer: "+name+", Not Found.");
    }

    public BeerNotFoundException(Long id) {
        super("Beer with ID: "+id+", Not Found.");
    }
}
