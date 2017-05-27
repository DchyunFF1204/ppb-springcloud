package com.ppb.client.runner;

import com.corundumstudio.socketio.SocketIOServer;
import com.ppb.handler.IMEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by daizy on 2017/5/27.
 */
@Component
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private SocketIOServer server;

    @Autowired
    private  IMEventHandler imEventHandler ;

    @Autowired
    public ServerRunner(SocketIOServer server) {
        this.server = server;
    }


    @Override
    public void run(String... args) throws Exception {
        // 添加监听事件
        //server.addListeners(imEventHandler);
        server.start();
        System.out.println("socket server begin");
    }
}
