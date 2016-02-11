package com.example.smd;

import android.content.Context;
import android.content.SharedPreferences;
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
        SharedPreferences preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("size",size());

        int i=0;

        Iterator iterator = collection.iterator();
        while(iterator.hasNext()){
           Persistable object = (Persistable) iterator.next();

           editor.putString("obj" + i,object.getId() + ";" + object.getType());
           object.save(context.getSharedPreferences(object.getId(),Context.MODE_PRIVATE));                      
           i++;
        }

        editor.commit();
     }

     public void load(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);

        int size = preferences.getInt("size",0);

        for(int i=0; i < size; i++) {
           String objInfo = preferences.getString("obj" + i,"");
           if(objInfo != ""){
                String objId = objInfo.substring(0,objInfo.indexOf(";"));
                String objType = objInfo.substring(objInfo.indexOf(";")+1);

                T obj = getObject(objType);
                obj.load(context.getSharedPreferences(objId,Context.MODE_PRIVATE));
                collection.add(obj);
           }          
        }

     }

     public T getObject(String type){
          try{
             Class c = Class.forName(type);
             return (T) c.newInstance();
          } catch(Exception ex) { return null; }
     }
}
