package com.ppb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppb.client.rabbit.Sender;

@RestController
public class RabbitMqController {
	
	@Autowired
	private Sender sender;
	
	@RequestMapping("/sendMq")
	public String sendMq(String message){
		sender.send(message);
		return "";
	}

}
