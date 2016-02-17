package com.example.smd;

import android.content.Context;
import android.database.sqlite.*;
import android.database.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.AbstractCollection;

public class PersistableCollection<T extends Persistable> extends AbstractCollection {

     Collection<T> collection;
     final String NAME = "persistablecollection";
	
     public PersistableCollection(Collection<T> collection){
          this.collection = collection;
     }

     public Iterator iterator(){
        return collection.iterator();
     }

     public int size(){
        return collection.size();
     }

     public void save(Context context){
        NotesDbHelper dbHelper = new NotesDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Iterator iterator = collection.iterator();
        while(iterator.hasNext()){
           Persistable object = (Persistable) iterator.next();
           object.save(db);                      
        }
     }

     public void load(Context context){
        NotesDbHelper dbHelper = new NotesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM Notes";
        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext()){
           T object = getObject("com.example.smd.Note");
           object.load(cursor);
           collection.add(object);
        }
     }

     public T getObject(String type){
          try{
             Class c = Class.forName(type);
             return (T) c.newInstance();
          } catch(Exception ex) { return null; }
     }
}
