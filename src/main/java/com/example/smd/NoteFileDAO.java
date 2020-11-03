package com.example.smd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class NoteFileDAO implements INoteDAO {
    File file;
    public NoteFileDAO(File f){
        file = f;
    }

    private boolean exists(String id){
        if (load(id) != null)
            return  true;
        return false;
    }


    @Override
    public void save(Hashtable<String, String> attributes) {
        if (exists(attributes.get("id"))){
            return;
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.append("[note]");
            writer.newLine();

            Enumeration<String> keys = attributes.keys();
            while (keys.hasMoreElements()){
                String key = keys.nextElement();
                writer.append(key + ":" + attributes.get(key));
                writer.newLine();
            }

            writer.close();

        }catch (Exception ex){

        }
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String> obj : objects){
            save(obj);
        }
    }

    @Override
    public ArrayList<Hashtable<String, String>> load() {
        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();
        try {
            Hashtable<String,String> obj = null;
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null){

                if(line.equals("[note]")){
                    obj = new Hashtable<String, String>();
                    objects.add(obj);
                }
                else {
                    String key = line.substring(0, line.indexOf(":"));
                    String value = line.substring(line.indexOf(":") + 1);
                    obj.put(key, value);
                }

            }

        } catch (Exception ex){
            int i=0;
        }

        return objects;
    }

    @Override
    public Hashtable<String, String> load(String id) {
        ArrayList<Hashtable<String,String>> objects = load();
        for(Hashtable<String,String> obj : objects){
            if (obj.get("id").equals(id)){
                return obj;
            }
        }

        return  null;
    }
}