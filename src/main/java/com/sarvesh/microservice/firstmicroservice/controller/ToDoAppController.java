package com.sarvesh.microservice.firstmicroservice.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sarvesh.microservice.firstmicroservice.data.ToDoDataObject;
import com.sarvesh.microservice.firstmicroservice.services.ToDoAppService;

@RestController
public class ToDoAppController {

	@Autowired
	private ToDoAppService appService;

	@GetMapping("/todos")
	public List<ToDoDataObject> getAllToDos() {
		return appService.getAllToDos();
	}

	@GetMapping("/todos/{id}")
	public ToDoDataObject getToDo(@PathVariable long id) {
		return appService.findByUId(id);
	}
	

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Void> deleteToDo(@PathVariable long id) {
		ToDoDataObject dataObject = appService.deleteToDo(id);

		if (dataObject != null) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<ToDoDataObject> updateToDo(@PathVariable long id,
			@RequestBody ToDoDataObject todo) {

		if (todo == null) {
			return ResponseEntity.noContent().build();
		}

		ToDoDataObject dataObject = appService.save(todo);

		return new ResponseEntity<ToDoDataObject>(dataObject, HttpStatus.OK);
	}
	

	@PostMapping("/todos")
	public ResponseEntity<Void> saveToDo(@RequestBody ToDoDataObject todo) {

		if (todo == null) {
			return ResponseEntity.noContent().build();
		}

		ToDoDataObject dataObject = appService.save(todo);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(dataObject.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
	

}
