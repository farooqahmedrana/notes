package com.example.smd;

import java.util.Date;
import java.util.UUID;
import java.io.Serializable;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Note implements Serializable,Persistable{
	
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

     public String getId(){
          return id;
     }
  
     public String getType(){
          return getClass().getName();
     }

     public void save(SharedPreferences dataStore){
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

          SharedPreferences.Editor editor = dataStore.edit();
          editor.putString("id",id);
          editor.putString("title",title);
          editor.putString("content",content);
          editor.putString("creationdatetime",dateFormat.format(creationDateTime));
          editor.putBoolean("important",important);

          editor.commit();
     }

     public void load(SharedPreferences dataStore){
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

          id = dataStore.getString("id","");
          title = dataStore.getString("title","");
          content = dataStore.getString("content","");
          important = dataStore.getBoolean("important",false);
          try{
              creationDateTime = dateFormat.parse(dataStore.getString("creationdatetime",""));
          }
          catch(ParseException ex){

          }

     }
}
