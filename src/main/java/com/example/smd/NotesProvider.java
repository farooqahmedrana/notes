package com.example.smd;

import android.database.sqlite.*;
import android.database.*;
import android.provider.*;
import android.content.*;
import android.net.*;
import android.util.*;

public class NotesProvider extends ContentProvider{

   private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

   static {
      matcher.addURI("com.example.smd.notesprovider","notes",1);
   }

   private NotesDbHelper dbHelper;
   private SQLiteDatabase db;
  
   public boolean onCreate(){
      dbHelper = new NotesDbHelper(getContext());
      db = dbHelper.getReadableDatabase();
      return true;
   }

   public String getType(Uri uri){
      return null;
   }

   public Uri insert(Uri uri,ContentValues values){
      return null;
   }

   public int update(Uri uri,ContentValues values,String selection,String[] args){
      return 0;
   }

   public int delete(Uri uri,String selection,String[] args){
      return 0;
   }
	
   public Cursor query(Uri uri,String [] projection,
                        String selection,String[] selectionArgs,String sortOrder){
      
      if(matcher.match(uri) == 1){
         	return db.query("Notes",projection,selection,selectionArgs,null,null,sortOrder);
/*         	Cursor cursor = db.rawQuery("select * from Notes",null);
          Log.i("Notes","--- size : " + cursor.getCount() + " ---" );
          return cursor;
*/      }

      Log.i("Notes","No match" );
      String[] cols = {"_ID"};
      return new MatrixCursor(cols);
   }
 
}
