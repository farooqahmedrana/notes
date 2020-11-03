package com.example.smd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;
import java.io.Serializable;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Note implements Serializable{
	
	private String id;
	private String title;
	private String content;
	private boolean important;
	private Date creationDateTime;
	private transient INoteDAO dao = null;
	
	public Note(){
		init();
	}
	
	public Note(String title,String content){
		init();
		this.title = title;
		this.content = content;
	}

    public Note(String title,String content,INoteDAO dao){
        init();
        this.title = title;
        this.content = content;
        this.dao = dao;
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
/*
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
*/
     public void save(){

	    if (dao != null){

            Hashtable<String,String> data = new Hashtable<String, String>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

            data.put("id",id);
            data.put("title",title);
            data.put("content",content);
            data.put("creationdatetime",dateFormat.format(creationDateTime));
            data.put("important",important?"true":"false");

            dao.save(data);
        }
     }

     public void load(Hashtable<String,String> data){
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

         id = data.get("id");
         title = data.get("title");
         content = data.get("content");
         try{
             important = Boolean.parseBoolean(data.get("important"));
             creationDateTime = dateFormat.parse(data.get("creationdatetime"));
         }
         catch(ParseException ex){

         }
     }

     public static ArrayList<Note> load(INoteDAO dao){
	    ArrayList<Note> notes = new ArrayList<Note>();
        if(dao != null){

            ArrayList<Hashtable<String,String>> objects = dao.load();
            for(Hashtable<String,String> obj : objects){
                Note note = new Note("","",dao);
                note.load(obj);
                notes.add(note);
            }
        }
        return notes;
     }
}
