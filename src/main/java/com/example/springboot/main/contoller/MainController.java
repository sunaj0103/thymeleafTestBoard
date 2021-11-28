package com.example.springboot.main.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String main(){
		return "redirect:/index";
	}
	
	@GetMapping("/error")
	public String error(){
		return "error";
	}
	
}
