package com.example.smd;

import java.util.Date;
import java.util.UUID;
import java.util.Hashtable;
import java.io.Serializable;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.*;
import android.util.Log;
import org.xmlpull.v1.*;

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

     public void save(OutputStream dataStore){
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");
/*
          SharedPreferences.Editor editor = dataStore.edit();
          editor.putString("id",id);
          editor.putString("title",title);
          editor.putString("content",content);
          editor.putString("creationdatetime",dateFormat.format(creationDateTime));
          editor.putBoolean("important",important);

          editor.commit();
*/
          String contents = "<map>";
          contents += "<string name='id' value='" + id + "'/>";
          contents += "<string name='title' value='" + title + "'/>";
          contents += "<string name='content' value='" + content + "'/>";
          contents += "<string name='creationdatetime' value='" + dateFormat.format(creationDateTime) + "'/>";
          contents += "<boolean name='important' value='" + important + "'/>";
          contents += "</map>";

          try{
             Writer writer = new OutputStreamWriter(dataStore,"UTF-8");
             writer.write(contents);
             writer.flush();
          } catch(Exception ex){ }

     }

     public void load(InputStream dataStore){
          try{

               SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");

               Hashtable<String,String> data = parse(dataStore);
               Log.i("Notes","size:" + data.size());
               id = data.get("id");
               title = data.get("title");
               content = data.get("content");
               Log.i("Notes","title:" + title);
               creationDateTime = dateFormat.parse(data.get("creationdatetime"));
               important = Boolean.parseBoolean(data.get("important"));
            
          } catch(Exception ex) { Log.i("Notes",ex.toString()); }

     }

     private Hashtable<String,String> parse(InputStream dataStore){
        Hashtable<String,String> data = new Hashtable<String,String>();
        
        try{
             XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
             parser.setInput(dataStore,"UTF-8");

             while(parser.next() != XmlPullParser.END_DOCUMENT){
                if(parser.getEventType() == XmlPullParser.START_TAG){
                   if (!parser.getName().equals("map")){
                      data.put(parser.getAttributeValue("","name"),
                               parser.getAttributeValue("","value"));
                   }
                }
             }
        } catch (Exception ex){ Log.i("Notes",ex.toString()); }

        return data;
     }
}
