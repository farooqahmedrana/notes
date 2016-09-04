package com.example.smd;

import android.app.*;
import android.os.*;
import android.content.*;
import android.widget.Toast;
import android.util.Log;
import java.net.*;
import java.io.*;
import android.content.*;
import android.provider.*;
import android.database.*;
import android.net.Uri;


public class NotesDataSyncService extends Service {

   private final IBinder binder = new LocalBinder();
   Messenger messenger = new Messenger(new IncomingHandler());
   static final int MSG_TEST = 1;

   public void onCreate() {

   }

   public int onStartCommand(Intent intent,int flags,int startId){
      Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();

      Thread thread = new Thread(new Runnable(){
         public void run(){
            post();
         }
      });

      thread.start();
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
                 sendReply(msg,msg.replyTo,getStatus());
            default:
               super.handleMessage(msg);
         }         
      }
   }

/*   public void handleDownloadMessage(final Message msg,final Messenger messenger){
      Thread thread = new Thread(new Runnable(){
         public void run(){
            sendReply(msg,messenger,postStatus());
         }
      });

      thread.start();
   }
*/
   public void sendReply(final Message msg,final Messenger receiver,String content){

      Message reply = Message.obtain(null,1);
      Bundle bundle = new Bundle();
      bundle.putString("result",content);
      reply.setData(bundle);

      try {
         receiver.send(reply);
      } catch (RemoteException ex) { ex.printStackTrace();}

   }

   public String getStatus(){
      return "synchronization in progress";
   }

   public String post(){

      StringBuilder content = new StringBuilder();

      try{

          URL url = new URL("http://10.0.2.2/notes/upload.php");
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
          connection.setReadTimeout(10000);
          connection.setConnectTimeout(15000);
          connection.setRequestMethod("POST");
          connection.setRequestProperty("Content-type","text/xml");
          connection.setDoOutput(true);
          connection.connect();

          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
          writer.write(getNotesContent());
          writer.flush();

          BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
          
          String line = "";
          while( (line = reader.readLine()) != null ){
             content.append(line);
          }

          connection.disconnect();

      } catch(Exception ex) {
           ex.printStackTrace();
      }

      return content.toString();
   }

   public String getNotesContent(){

      String uri = "content://com.example.smd.notesprovider/notes";
      Cursor cursor = getContentResolver().query(Uri.parse(uri),null,null,null,null);
              
      StringBuilder result = new StringBuilder();
      while (cursor != null && cursor.moveToNext()){
         String title = cursor.getString(cursor.getColumnIndex("Title"));
         String content = cursor.getString(cursor.getColumnIndex("Content"));
         result.append ("<note title='" + title + "' content='" + content + "'/>");
      }

      return "<notes>" + result.toString() + "</notes>";
   }


}

