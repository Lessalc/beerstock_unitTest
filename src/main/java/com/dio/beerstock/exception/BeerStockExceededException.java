package com.dio.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockExceededException extends Exception {
    public BeerStockExceededException(Long id, int quantityToIncrement) {
        super("Beer with Id: "+id+", informs increment of"+quantityToIncrement+" exceed max capacity in stock");
    }
}
