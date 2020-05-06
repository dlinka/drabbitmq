package com.cr.asynProcess;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/register-success")
    public String send(){
        amqpTemplate.convertAndSend("user_register_success_exchange", "", "userId:27");
        return "ok";
    }

}
