package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditController {
	@GetMapping("/edit")
	String edit() {
		return "edit" ;
	}
}
