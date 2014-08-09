package com.codepath.todopersist;

public class Task {
	private int id;
    private String task;
    private String date;
    private int priority;
    private int completed;
    
    public Task(){
        
    }
    
    public Task(int id, String task, String date, int priority, int completed){
        this.id = id;
        this.task= task;
        this.date = date;
        this.priority = priority;
        this.completed = completed;
    }
    
    public Task(String task, String date, int priority, int completed){
    	this.task= task;
        this.date = date;
        this.priority = priority;
        this.completed = completed;
    }
    
    public Task(String task){
    	this.task= task;
        this.completed = 0;
    }
    
	public int getId() {
		return id;
	}	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTask() {
		return task;
	}	
	public void setTask(String task) {
		this.task = task;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public void alertPriotiry(int priority){
		this.priority = (priority==1)? 0:1;
	}
	
	public int getCompleted() {
		return completed;
	}
	public void setCompleted(int completed) {
		this.completed = completed;
	}	
	public void alertCompleted(int completed){
		this.completed = (completed==1)? 0:1;
	}
	
}
