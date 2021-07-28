package com.dio.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerAlreadyRegisteredException extends Exception {

    public BeerAlreadyRegisteredException(String name){
        super("Beer: "+name+". Already Registered.");
    }
}
