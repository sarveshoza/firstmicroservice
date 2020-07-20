package com.sarvesh.microservice.firstmicroservice.controller;

import com.sarvesh.microservice.firstmicroservice.data.ToDoDataObject;
import com.sarvesh.microservice.firstmicroservice.services.ToDoAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ToDoAppController {

    @Autowired
    private ToDoAppService doAppService;

    @GetMapping("/todos")
    public List<ToDoDataObject> getAllToDos() {
        return doAppService.getAllToDos();
    }

    @GetMapping("/todos/{id}")
    public ToDoDataObject getJPAToDo(@PathVariable long id) {
        return doAppService.findByUId(id);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteJPAToDo(@PathVariable long id) {
        ToDoDataObject dataObject = doAppService.deleteToDo(id);

        if (dataObject != null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<ToDoDataObject> updateJPAToDo(@PathVariable long id,
                                                        @RequestBody ToDoDataObject todo) {

        if (todo == null) {
            return ResponseEntity.noContent().build();
        }

        ToDoDataObject dataObject = doAppService.save(todo);

        return new ResponseEntity<ToDoDataObject>(dataObject, HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<Void> saveJPAToDo(@RequestBody ToDoDataObject todo) {

        if (todo == null) {
            return ResponseEntity.noContent().build();
        }

        ToDoDataObject dataObject = doAppService.save(todo);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(dataObject.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}