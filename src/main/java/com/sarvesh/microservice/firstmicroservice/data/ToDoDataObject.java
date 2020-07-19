package com.sarvesh.microservice.firstmicroservice.data;

import java.util.Date;

public class ToDoDataObject {

	private Long id;
	private String task;
	private Date targetDate;
	
	public ToDoDataObject() {
		super();
	}
	
	public ToDoDataObject(Long id,  String task, Date targetDate, boolean done) {
		super();
		this.id = id;
		this.task = task;
		this.targetDate = targetDate;
		this.done = done;
	}
	private  boolean done;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public Date getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDoDataObject other = (ToDoDataObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}

