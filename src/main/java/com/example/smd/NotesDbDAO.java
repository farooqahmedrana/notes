package com.example.smd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class NotesDbDAO implements INoteDAO {

    private Context context;

    public NotesDbDAO(Context ctx){
        context = ctx;
    }

    @Override
    public void save(Hashtable<String, String> attributes) {
        NotesDbHelper dbHelper = new NotesDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key,attributes.get(key));
        }

        db.insert("Notes",null,content);
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String> obj : objects){
            save(obj);
        }
    }

    @Override
    public ArrayList<Hashtable<String, String>> load() {
        NotesDbHelper dbHelper = new NotesDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Notes";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();
        while(cursor.moveToNext()){
            Hashtable<String,String> obj = new Hashtable<String, String>();
            String [] columns = cursor.getColumnNames();
            for(String col : columns){
                obj.put(col.toLowerCase(),cursor.getString(cursor.getColumnIndex(col)));
            }
            objects.add(obj);
        }

        return objects;
    }

    @Override
    public Hashtable<String, String> load(String id) {
        return null;
    }
}
