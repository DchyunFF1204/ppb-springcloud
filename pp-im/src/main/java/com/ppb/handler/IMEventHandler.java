package com.ppb.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ppb.client.redis.RedisHelper;
import com.ppb.model.message.MessageInfo;
import com.ppb.model.user.ClientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


/**
 * IM 事件处理器
 *
 * 用户基于redis 缓存
 *
 */
@Component
public class IMEventHandler {

	private final SocketIOServer server;

	Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired  
    public IMEventHandler(SocketIOServer server) {
        this.server = server ;
    }

	/**
	 * 链接 NIO
	 * @param client
	 */
	@OnConnect
    public String onConnect(SocketIOClient client) {
    	try {
			String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
			ClientInfo clientInfo = gson.fromJson(RedisHelper.getRedisInstance().get("",clientId),ClientInfo.class) ;
			if (clientInfo != null){
				Date nowTime = new Date(System.currentTimeMillis());
				clientInfo.setConnected((short)1);
				clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
				clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
				clientInfo.setLastconnecteddate(nowTime);
				RedisHelper.getRedisInstance().set("",clientId, gson.toJson(clientInfo));
			}
			return gson.toJson(clientInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
      
    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息  
    @OnDisconnect  
    public void onDisconnect(SocketIOClient client) {
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		ClientInfo clientInfo =  gson.fromJson(RedisHelper.getRedisInstance().get("",clientId),ClientInfo.class) ;
		if (clientInfo != null){
			clientInfo.setConnected((short)0);
			clientInfo.setMostsignbits(null);
			clientInfo.setLeastsignbits(null);
			RedisHelper.getRedisInstance().set("",clientId, gson.toJson(clientInfo));
		}
	}

    //消息接收入口，收发消息
    @OnEvent(value="messageevent")
	public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
		String targetClientId = data.getTargetClientId();
		ClientInfo clientInfo =gson.fromJson(RedisHelper.getRedisInstance().get("",targetClientId),ClientInfo.class);
		if (clientInfo != null && clientInfo.getConnected() != 0) {
			UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
			MessageInfo sendData = new MessageInfo();
			sendData.setSourceClientId(data.getSourceClientId());
			sendData.setTargetClientId(data.getTargetClientId());
			sendData.setMsgType("chat");
			sendData.setMsgContent(data.getMsgContent());
			client.sendEvent("messageevent", sendData);
			server.getClient(uuid).sendEvent("messageevent", sendData);
		}
	}

}