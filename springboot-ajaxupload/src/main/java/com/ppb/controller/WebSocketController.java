package com.ppb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * @author daizy
 * 
 *         websocket io
 *
 */
@Controller
public class WebSocketController {

	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;

	@MessageMapping("/hello")
	public String greeting(String name) throws Exception {
		String text = "[" + System.currentTimeMillis() + "]:" + name;  
		simpMessageSendingOperations.convertAndSend("/queue/greetings", text);  
		return text;
	}

}
