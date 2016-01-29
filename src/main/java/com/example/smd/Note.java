package com.example.smd;

import java.util.Date;
import java.util.UUID;
import java.io.Serializable;

public class Note implements Serializable{
	
	private String id;
	private String content;
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
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
}
