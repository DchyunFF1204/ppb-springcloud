package com.ppb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by daizy on 2017/5/27.
 */
@Controller
public class SocketIoController {

    @RequestMapping("/socket")
    public String intoSocket(){
        return "socketio";
    }
}
