package com.example.smd;

import java.util.Date;
import java.util.UUID;

public class Note {
	
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
	
}
