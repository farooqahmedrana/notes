package com.example.smd;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.AbstractCollection;
import org.xmlpull.v1.*;
import android.util.Log;

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
        Log.i("Notes"," ******** " + size());
        String header = "<objects size='" + size() + "' >";
        String footer = "</objects>";

        BufferedOutputStream stream = null;
        try{
           File file = new File(context.getFilesDir(), NAME);
           stream = new BufferedOutputStream(new FileOutputStream(file));
           Writer writer = new OutputStreamWriter(stream,"UTF-8");
           writer.write(header);

           Iterator iterator = collection.iterator();
           while(iterator.hasNext()){
              Persistable object = (Persistable) iterator.next();
              writer.write("<object id='" + object.getId() + "' type='" + object.getType() + "'>");

              ByteArrayOutputStream objectStream = new ByteArrayOutputStream();
              object.save(objectStream);
              writer.write(new String(objectStream.toByteArray(),"UTF-8"));

              writer.write("</object>");
           }

           writer.write(footer);
           writer.close();
        }
        catch(Exception ex){

        }
        finally{
           if (stream != null) try { stream.close(); } catch(Exception ex) { }
        }

     }

     public void load(Context context){
         try{
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            File file = new File(context.getFilesDir(), NAME);
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
            parser.setInput(stream,"UTF-8");

            while(parser.next() != XmlPullParser.END_DOCUMENT){
               if(parser.getEventType() == XmlPullParser.START_TAG &&
                  parser.getName().equals("object")){

                  T object = getObject(parser.getAttributeValue("","type"));
                  String contents = readInnerContents(parser);
                  object.load(new ByteArrayInputStream(contents.getBytes("UTF-8")));
                  collection.add(object);

               }
            }

         } catch(Exception ex){ }

     }

     public T getObject(String type){
          try{
             Class c = Class.forName(type);
             return (T) c.newInstance();
          } catch(Exception ex) { return null; }
     }

     private String readInnerContents(XmlPullParser parser) throws XmlPullParserException,IOException{
        StringBuilder builder = new StringBuilder();
 
        int depth = 1;
        
        while (depth != 0){
           parser.next();
           if(parser.getEventType() == XmlPullParser.START_TAG){
              StringBuilder attributes = new StringBuilder();
              for(int i=0; i < parser.getAttributeCount(); i++){
                 attributes.append(" " + parser.getAttributeName(i) + "='" + parser.getAttributeValue(i) + "'");
              }

              builder.append("<" + parser.getName() + attributes.toString() + ">");
              depth++;
           }
           else if(parser.getEventType() == XmlPullParser.END_TAG){
              depth--;
              if(depth != 0){
                 builder.append("</" + parser.getName() + ">");
              }
           }
           else{
              builder.append(parser.getText());
           }
        }

        return builder.toString();
     }
}
