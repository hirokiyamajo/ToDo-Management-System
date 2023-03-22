package com.dmm.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Todo;
import com.dmm.task.data.repository.TodoRepository;

@Controller
public class MainController {
	@Autowired
	private TodoRepository repo;
	
	@GetMapping("/testmain")
	String main(Model model) {
		List<Todo> main =  repo.findAll();
		model.addAttribute("testmain", main);
		return "main";
	}

}
