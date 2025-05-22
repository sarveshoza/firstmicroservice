package com.sarvesh.microservice.firstmicroservice.services;

import java.util.List;

import com.sarvesh.microservice.firstmicroservice.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvesh.microservice.firstmicroservice.data.ToDoDataObject;

@Service
public class ToDoAppService {

	@Autowired
	private ToDoRepository toDoRepository;

	public List<ToDoDataObject> getAllToDos() {
		return toDoRepository.findAll();
	}

	public ToDoDataObject save(ToDoDataObject dataObject) {
		if (null == dataObject.getId()) {
			toDoRepository.save(dataObject);
		} else {
			deleteToDo(dataObject.getId());
		}

		return dataObject;

	}

	public ToDoDataObject deleteToDo(long id) {

		return toDoRepository.findById(id).get();

	}

	public ToDoDataObject findByUId(long id) {

		return toDoRepository.findById(id).get();
	}

}
