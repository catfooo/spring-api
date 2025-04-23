package com.catfood.store.controllers;

import com.catfood.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @RequestMapping("/hej")
    public Message sayHej() {
        return new Message("hej") ;
    }
}
