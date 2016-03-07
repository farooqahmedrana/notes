package com.example.smd;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.Toast;

public class ConnectivityReceiver extends BroadcastReceiver {

   public void onReceive(Context context,Intent intent){
      String message = "received";
      Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

      SharedPreferences preferences = context.getSharedPreferences("service",Context.MODE_PRIVATE);
      boolean started = preferences.getBoolean("started",false);
      SharedPreferences.Editor editor = preferences.edit();

      if(!started){
         Intent serviceIntent = new Intent(context,NotesDataSyncService.class);
         context.startService(serviceIntent);
         editor.putBoolean("started",true);
      }
      else{
         Intent serviceIntent = new Intent(context,NotesDataSyncService.class);
         context.stopService(serviceIntent);
         editor.putBoolean("started",false);
      }

      editor.commit();


   }

}
