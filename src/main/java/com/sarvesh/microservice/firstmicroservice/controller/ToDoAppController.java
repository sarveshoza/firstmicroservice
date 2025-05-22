package com.sarvesh.microservice.firstmicroservice.controller;

import com.sarvesh.microservice.firstmicroservice.data.ToDoDataObject;
import com.sarvesh.microservice.firstmicroservice.services.ToDoAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @PostMapping(value = "/todos/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) throws IOException {

        String dir = "D://temp/";  // change the dir path as per file system
        File saveFile = new File(dir+file.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(saveFile);
        outputStream.write(file.getBytes());
        return new ResponseEntity<>("File is uploaded successfully",HttpStatus.OK);
    }

    @GetMapping(value = "/todos/downloads/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> download(@PathVariable String filename) throws IOException {

        String dir = "D://temp/";
        File download = new File(dir+filename);

        if(!download.exists()){
            return new ResponseEntity<>("Failure, File nor found",HttpStatus.NOT_FOUND);
        }else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%S\"",filename));
            InputStreamResource resource = new InputStreamResource(new FileInputStream(download));
            ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(download.length())
                    .contentType(MediaType.valueOf(Files.probeContentType(Paths.get(download.getAbsolutePath())))).body(resource);
            return responseEntity;
        }

    }
}