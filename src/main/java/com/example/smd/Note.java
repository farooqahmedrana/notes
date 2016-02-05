package com.example.smd;

import java.util.Date;
import java.util.UUID;
import java.io.Serializable;

public class Note implements Serializable{
	
	private String id;
     private String title;
	private String content;
	private boolean important;
	private Date creationDateTime;
	
	public Note(){
		init();
	}
	
	public Note(String title,String content){
		init();
		this.title = title;
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

	public void setTitle(String title){
		this.title = title;
	}
	
	public String getContent(){
		return content;
	}

	public String getTitle(){
		return title;
	}

	public void setImportance(boolean value){
		important = value;
	}
	
	public boolean isImportant(){
		return important;
	}

     public boolean contains(String text){
		return getTitle().contains(text) || getContent().contains(text);
	}
}
