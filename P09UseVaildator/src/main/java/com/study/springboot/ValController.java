package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ValController {
	@RequestMapping("/")
	public String root() {
		return "insertForm";
	}
	
	@RequestMapping("/done")
	public String insert(@ModelAttribute("dto") ContentDTO contentDTO, BindingResult result) {
		
		System.out.println(contentDTO);
		String page = "donePage";
		ContentValidator validator = new ContentValidator();
		validator.validate(contentDTO, result);
		if(result.hasErrors()) {
			page="insertForm";
		} return page;
		
		
	}
}
