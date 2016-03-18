package com.example.smd;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.Toast;

public class NotesDataSyncService extends Service {

   private final IBinder binder = new LocalBinder();
   Messenger messenger = new Messenger(new IncomingHandler());
   static final int MSG_TEST = 1;

   public void onCreate() {

   }

   public int onStartCommand(Intent intent,int flags,int startId){
      Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();
      return START_NOT_STICKY;
   }

   public IBinder onBind(Intent intent){
//      return binder;
        return messenger.getBinder();
   }

   public void onDestroy(){
      Toast.makeText(this,"Service stopped",Toast.LENGTH_SHORT).show();
   }

   public class LocalBinder extends Binder{
      public NotesDataSyncService getService(){
         return NotesDataSyncService.this;
      }
   }

   public class IncomingHandler extends Handler{
 
      public void handleMessage(Message msg){
         switch(msg.what){
            case MSG_TEST:
//               Toast.makeText(getApplicationContext(),getStatus(),Toast.LENGTH_SHORT).show();
                 Message reply = Message.obtain(null,1);
                 Bundle bundle = new Bundle();
                 bundle.putString("result",getStatus());
                 reply.setData(bundle);
                 Messenger receiver = msg.replyTo;
                 try {
                    receiver.send(reply);
                 } catch (RemoteException ex) { ex.printStackTrace();}

            default:
               super.handleMessage(msg);
         }         
      }
   }



   public String getStatus(){
      return "synchronization in progress";
   }

}
