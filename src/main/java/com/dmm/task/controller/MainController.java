package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Todo;
import com.dmm.task.data.repository.TodoRepository;

@Controller
public class MainController {
	@Autowired
	private TodoRepository repo;
	
	@GetMapping("/main")
	String main(Model model) {
		List<Todo> main =  repo.findAll();
		model.addAttribute("main", main);
		
		//1. 2次元表になるので、ListのListを用意
		List<List<LocalDate>> calendar = new ArrayList<>();
		
		//2. 1週間分のLocalDateを格納するListを用意
		List<LocalDate> week = new ArrayList<>();
		
		//3. その月の1日のLocalDateを取得
		LocalDate current = LocalDate.now().withDayOfMonth(1);
		
		// 4. 曜日を表すDayOfWeekを取得
		DayOfWeek w1 = current.getDayOfWeek();
		//取得したLocalDateに曜日の値（DayOfWeek#getValue)をマイナスして前月分のLocalDateを求める
		current = current.minusDays(w1.getValue());
		
		//5.1日ずつ増やしてLocalDateを求めていき、2．で作成したListへ格納していき、1週間分詰めたら1．のリストへ格納
		for(int i = 1; i<= 7; i++) {
			current =  current.plusDays(1);
			week.add(current);
		}
		calendar.add(week);
		week = new ArrayList<>();
		
		/**
		 * 6. 2週目以降は単純に1日ずつ日を増やしながらLocalDateを求めてListへ格納していき、
		 * 土曜日になったら1．のリストへ格納して新しいListを生成する（月末を求めるにはLocalDate#lengthOfMonth()を使う）
		 */
		for(int i = 7; i <= current.lengthOfMonth(); i++) {
			current =  current.plusDays(1);
			week.add(current);
			if (w1 == DayOfWeek.SATURDAY) {
				System.out.println();
				calendar.add(week);
				week = new ArrayList<>();
			}
		}
		
		//7. 最終週の翌月分をDayOfWeekの値を使って計算し、6．で生成したリストへ格納し、最後に1．で生成したリストへ格納
		LocalDate end = current.withDayOfMonth(current.lengthOfMonth());
		DayOfWeek lastDayOfWeek = end.getDayOfWeek();
		int daysToAdd = 6 - lastDayOfWeek.getValue();
		for (int i = 1; i <= daysToAdd; i++) {
			LocalDate date = end.plusDays(i);
			week.add(date);
		}
		calendar.add(week);
		
		//8. 管理者は全員分のタスクを見えるようにする
		MultiValueMap<LocalDate, Todo> tasks = new LinkedMultiValueMap<LocalDate, Todo>();
		model.addAttribute("tasks", tasks);

		return "main";
	}
	
	@PostMapping("/main")
	public String redisterTodo() {
		return "redirect:/main";
	}
}
