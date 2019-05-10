package com.neo.controller;



 import java.util.ArrayList;
 import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;


@Service
public class HelloWorldService {

    @Autowired
    private HelloWorldService mapper;

    public String addMessage(String appMessage) {
        return appMessage;
    }
}
