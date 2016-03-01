package com.example.smd;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.Toast;

public class NotesDataSyncService extends Service {

   private final IBinder binder = new LocalBinder();

   public void onCreate() {

   }

   public int onStartCommand(Intent intent,int flags,int startId){
      Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();
      return START_NOT_STICKY;
   }

   public IBinder onBind(Intent intent){
      return binder;
   }

   public class LocalBinder extends Binder{
      public NotesDataSyncService getService(){
         return NotesDataSyncService.this;
      }
   }

   public String getStatus(){
      return "synchronization in progress";
   }

}
