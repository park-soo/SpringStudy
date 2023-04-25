package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LombokController {
	@RequestMapping("/")
	public String root() {
		return "form";
	}
	
	@RequestMapping("/test1")
	public String test1(@ModelAttribute("person") PersonDTO dto, Model model) {
		//model.addAttribute("person",dto);
		System.out.println(dto.toString());
		return "test1";
	}
	
}
