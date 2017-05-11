package com.ppb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ppb.client.socket.ToUserMessage;

/**
 * @author daizy
 * 
 *         websocket io
 *
 */
@Controller
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/hello")
	@SendTo("/queue/greetings") //频道
	public String greeting(String name) throws Exception {
		String text = "[" + System.currentTimeMillis() + "]:" + name;
		return text;
	}
	
	/**
	 * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message”
	 * @param message
	 * @return
	 */
	@MessageMapping("/message")  
    @SendToUser("/queue/message")  
    public void handleSubscribe() throws Exception{  
        
    }  

	 /** 
     * 指定用户发送消息
     * @return 
     */  
    @RequestMapping("/send")
    @ResponseBody
    public void send(ToUserMessage message) {  
    	System.out.println(message.getMessage());
    	template.convertAndSend("/queue/message"+message.getUserId(), message);
    }  

}
