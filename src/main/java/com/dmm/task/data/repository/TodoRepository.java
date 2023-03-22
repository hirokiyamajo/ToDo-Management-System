package com.dmm.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmm.task.data.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{

	
}
