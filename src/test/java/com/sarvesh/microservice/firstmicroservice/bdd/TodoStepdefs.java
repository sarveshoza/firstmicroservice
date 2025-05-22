package com.sarvesh.microservice.firstmicroservice.bdd;

import com.sarvesh.microservice.firstmicroservice.data.ToDoDataObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;

public class TodoStepdefs {

    @LocalServerPort
    protected int port;

    protected final String SERVER_URL = "http://localhost";
    protected final String API = "/todos";

    protected RestTemplate restTemplate = new RestTemplate();

    ToDoDataObject toDoDataObject = null;
    int status  = 0;
    int searchId  = 0;

    private String apiEndpoint() {
        return SERVER_URL + ":" + port + API;
    }

    @Given("todo task {string}, targetDate {string}")
    public void todoTaskBDDLearningTargetDate(String task, String targetDate) {
        toDoDataObject = new ToDoDataObject();
        toDoDataObject.setTargetDate(Date.valueOf(targetDate));
        toDoDataObject.setTask(task);
        toDoDataObject.setDone(false);
    }

    @When("creating TODO")
    public void creatingTODO() {
       status =  restTemplate.postForEntity(apiEndpoint(), toDoDataObject,ToDoDataObject.class).getStatusCodeValue();
    }

    @Then("TODOs list should contain newly crated TODO")
    public void todosListShouldContainNewlyCratedTODO() {
        Assert.assertEquals(status, 201);
    }

    @Given("todo id {int}")
    public void todo_id(Integer int1) {
        searchId = int1;
    }

    @When("search in application")
    public void search_in_application() {
        String searchApi = apiEndpoint()+"/"+searchId;
        toDoDataObject = restTemplate.getForEntity(searchApi,ToDoDataObject.class).getBody();
    }
    @Then("result should return {string} Todo")
    public void result_should_return_todo(String string) {
        Assert.assertEquals(string, toDoDataObject.getTask());
    }



}
