package com.gtask.core;

import java.util.ArrayList;

public class TaskList {

	private ArrayList<Task> tasks = new ArrayList<Task>();
	
	private String name = ""; 
	private String Id 	= ""; 
	
	public TaskList add(Task task){
		this.tasks.add(task);
		return this;
	}
	
	public ArrayList<Task> getTasks(){
		return this.tasks; 
	}
	
	public Task getTask(int index){
		return this.tasks.get(index);
	}
	
	public TaskList setName(String name){
		this.name = name; 
		return this; 
	}
	
	public TaskList setId(String id){
		this.Id = id; 
		return this; 
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return Id;
	}
}
