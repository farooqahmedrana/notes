package com.example.smd;

import java.util.Date;
import java.util.UUID;
import java.io.Serializable;
import android.content.ContentValues;
import android.database.sqlite.*;
import android.database.*;
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

     public void save(SQLiteDatabase dataStore){
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

          ContentValues values = new ContentValues();
          values.put("Id",id);
          values.put("Title",title);
          values.put("Content",content);
          values.put("Important",important);
          values.put("CreationDateTime",dateFormat.format(creationDateTime));

          dataStore.insertWithOnConflict("Notes",null,values,SQLiteDatabase.CONFLICT_REPLACE);
     }

     public void load(Cursor dataStore){
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

          id = dataStore.getString(dataStore.getColumnIndex("Id"));
          title = dataStore.getString(dataStore.getColumnIndex("Title"));
          content = dataStore.getString(dataStore.getColumnIndex("Content"));
          important = dataStore.getInt(dataStore.getColumnIndex("Important")) == 1 ? true : false;
          try{
              creationDateTime = dateFormat.parse(dataStore.getString(dataStore.getColumnIndex("CreationDateTime")));
          }
          catch(ParseException ex){

          }

     }
}
