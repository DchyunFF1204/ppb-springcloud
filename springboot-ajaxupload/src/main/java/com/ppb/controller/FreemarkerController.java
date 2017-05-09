package com.ppb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FreemarkerController {
	
	@RequestMapping("/testftl")
	public String intoTestFtl(){
		return "test";
	}

}
