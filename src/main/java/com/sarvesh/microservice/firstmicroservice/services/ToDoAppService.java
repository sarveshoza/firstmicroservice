package com.sarvesh.microservice.firstmicroservice.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sarvesh.microservice.firstmicroservice.data.ToDoDataObject;

@Service
public class ToDoAppService {
	
	
	private static List<ToDoDataObject> todos = new ArrayList<ToDoDataObject>();
	private static long idCounter=0;
	
	static {
		todos.add(new ToDoDataObject(++idCounter,  "Spring Boot Learn", new Date(), false));
		todos.add(new ToDoDataObject(++idCounter,  "React Learn", new Date(), false));
	}
	
	public List<ToDoDataObject> getAllToDos(){
		return todos;
	}
	
	public ToDoDataObject save(ToDoDataObject dataObject) {
		if(dataObject.getId()==-1) {
			dataObject.setId(++idCounter);
			todos.add(dataObject);
		}else {
			deleteToDo(dataObject.getId());
			todos.add(dataObject);
		}
		
		return dataObject;
		
	}
	
	public ToDoDataObject deleteToDo(long id) {
		
		ToDoDataObject dataObject  = findByUId(id);
		
		if(null==dataObject) {
			return null;
		}else {
			todos.remove(dataObject);
		}
		return dataObject;
		
	}

	public ToDoDataObject findByUId(long id) {
		
		for(ToDoDataObject dataObject : todos) {
			if(dataObject.getId()==id) {
				return dataObject;
			}
		}
		return null;
	}
	
	
}
