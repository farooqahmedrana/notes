package com.example.smd;

import java.util.Date;
import java.util.UUID;
import java.io.Serializable;

public class Note implements Serializable{
	
	private String id;
	private String content;
	private boolean important;
	private Date creationDateTime;
	
	public Note(){
		init();
	}
	
	public Note(String content){
		init();
		this.content = content;
	}
	
	private void init(){
		this.id = UUID.randomUUID().toString();		
		this.creationDateTime = new Date();
		this.important = false;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setImportance(boolean value){
		important = value;
	}
	
	public boolean isImportant(){
		return important;
	}
	
}
