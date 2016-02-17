package com.example.smd;

import android.database.sqlite.*;
import android.database.*;
import android.content.Context;

public class NotesDbHelper extends SQLiteOpenHelper{
	
   public static final int DATABASE_VERSION = 1;
   public static final String DATABASE_NAME = "Notes.db";

   public NotesDbHelper(Context context){
      super(context,DATABASE_NAME,null,DATABASE_VERSION);
   }

   public void onCreate(SQLiteDatabase db){
      String sql = "CREATE TABLE Notes (Id TEXT PRIMARY KEY, " +
                                       "Title TEXT," + 
                                       "Content TEXT," + 
                                       "Important INTEGER," + 
                                       "CreationDateTime TEXT)";
      db.execSQL(sql);
   }

   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS Notes");
      onCreate(db);
   }

   public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      onUpgrade(db,oldVersion,newVersion);
   }

}
