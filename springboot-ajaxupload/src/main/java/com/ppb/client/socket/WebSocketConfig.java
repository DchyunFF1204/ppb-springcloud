package com.ppb.client.socket;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("com.ppb.controller.WebSocketController")  
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	
	/** 
     * 将"/hello"路径注册为STOMP端点，这个路径与发送和接收消息的目的路径有所不同，这是一个端点，客户端在订阅或发布消息到目的地址前，要连接该端点， 
     * 即用户发送请求url="/applicationName/hello"与STOMP server进行连接。之后再转发到订阅url； 
     * PS：端点的作用——客户端在订阅或发布消息到目的地址前，要连接该端点。 
     * @param stompEndpointRegistry 
     */  
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/hello")
		.setAllowedOrigins("*").withSockJS();
	}
	
	/** 
     * 配置了一个简单的消息代理，如果不重载，默认情况下回自动配置一个简单的内存消息代理，用来处理以"/topic"为前缀的消息。这里重载configureMessageBroker()方法， 
     * 消息代理将会处理前缀为"/topic"和"/queue"的消息。 
     * @param registry 
     */ 
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		//config.enableSimpleBroker("/topic","/user");   //默认内存代理 
		config.enableStompBrokerRelay("/topic","/queue")
			.setRelayHost("192.168.0.51")  // rabbitmq 代理
	        .setRelayPort(61613)
			.setClientLogin("test")  
			.setClientPasscode("111111")
			.setSystemHeartbeatSendInterval(5000)
			.setSystemHeartbeatReceiveInterval(4000);
        config.setApplicationDestinationPrefixes("/app");  
        //config.setUserDestinationPrefix("/user");
       
	}

}
