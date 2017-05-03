package com.ppb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FreemarkerController {
	
	@RequestMapping("/intoTestPage")
	public String intoTestPage(Model model){
		model.addAttribute("name", "hello springboot-freemarker!");
		return "test";
	}

}
