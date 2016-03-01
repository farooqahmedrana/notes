package com.example.smd;

import java.util.ArrayList;
import java.io.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import android.widget.CheckBox;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.accounts.AccountManager;
import android.accounts.Account;
import android.content.*;
import android.provider.*;
import android.os.*;

public class NotesActivity extends BaseActivity
{
	ArrayList<Note> notes;
	Note currentNote;
	
	EditText textContent;
	EditText textTitle;

	TextWatcher watcher;
	CheckBox importanceCheck;
	
	final int REQUEST_CODE = 1; 
     final int MENU_SEND = 1;

    NotesDataSyncService dataService;
    boolean bound = false;


	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        notes = new ArrayList<Note>();
        setContentView(R.layout.main);        
        importanceCheck = (CheckBox) findViewById(R.id.importance_check);
        textContent = (EditText) findViewById(R.id.text_content);
        textTitle = (EditText) findViewById(R.id.text_title);
        textContent.addTextChangedListener(getWatcher());        
        handleIntent();
        Intent intent = new Intent(this,NotesDataSyncService.class);
        startService(intent);
    }

    private void handleIntent(){
        Intent intent = getIntent();
        
        if (intent != null ){
            Uri uri = intent.getData();
            if(uri != null && uri.getScheme().equals("note")){
             String text = intent.getStringExtra("message");
             textContent.setText(text);
            }
        }
    }


    @Override    
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(0,MENU_SEND,0,"Send");
        return true;
    }

     @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case MENU_SEND:
				sendMenu();
				return true;
               default:
                    return super.onOptionsItemSelected(item);
          }
     }  

    public void onPause(){
       super.onPause();
       PersistableCollection<Note> collection = new PersistableCollection(notes);
       collection.save(getApplicationContext());
    }

    public void onResume(){
       super.onResume();
       notes = new ArrayList<Note>();
       PersistableCollection<Note> collection = new PersistableCollection(notes);
       collection.load(getApplicationContext());
    }
    

    protected void newMenu(){
    	newNote();
    }
    
    protected void listMenu(){
    	listNotes();
    }

    protected void sendMenu(){
     sendNote();
    }

    public void checkboxClick(View v){
    	if(currentNote != null){
	    
    		boolean checked = ((CheckBox) v).isChecked();
    		currentNote.setImportance(checked);
	    		    	
    	}
    }
    
    private void saveNote(){
	   String title = textTitle.getText().toString();
	   String content = textContent.getText().toString();

	   if(currentNote == null){
		 currentNote = new Note(title,content);
		 notes.add(currentNote);
	   }

	   currentNote.setImportance(importanceCheck.isChecked());
	   currentNote.setContent(content);
	   currentNote.setTitle(title);
    }
    
    private void newNote(){
    	textContent.removeTextChangedListener(getWatcher());     
    	textContent.setText("");
    	textContent.addTextChangedListener(getWatcher());
     textTitle.setText("");
    	importanceCheck.setChecked(false);    	  
    	currentNote = null;
    }
       
    private void listNotes(){
    	   Intent intent = new Intent(this,ListActivity.class);
    	   intent.putExtra("list",notes);
    	   startActivityForResult(intent,REQUEST_CODE);
    }

    private void sendNote(){
	   Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"));
    	   intent.putExtra("sms_body",currentNote.getContent());

        if(intent.resolveActivity(getPackageManager()) != null){
    	      startActivity(intent);
        }

    }

    private void showMessage(String message){
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        toast.show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       	super.onActivityResult(requestCode, resultCode, data);
       	if(requestCode == REQUEST_CODE){
       		if(resultCode == RESULT_OK){
//       			notes = (ArrayList<Note>) data.getSerializableExtra("list");
       			
       			int selectedItemIndex = data.getIntExtra("selecteditemindex", -1);
       			if(selectedItemIndex != -1){
       				setNote(selectedItemIndex);       				
       			}
       		}
       	}
    }
    
    private void setNote(int index){
    	currentNote = notes.get(index);
    	textContent.setText(currentNote.getContent());
    	importanceCheck.setChecked(currentNote.isImportant());
    }
    
    private TextWatcher getWatcher(){
    	if(watcher == null){
	    	watcher = new TextWatcher(){
	
	        	public void afterTextChanged(Editable arg0) {
	        		saveNote();
				}
	
				public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) { }
	
				@Override
				public void onTextChanged(CharSequence s, int start, int before,int count) { }
	        	
	        };
    	}
    	
    	return watcher;
    }

   private ServiceConnection connection = new ServiceConnection(){

      public void onServiceConnected(ComponentName className, IBinder binder){
         dataService = ((NotesDataSyncService.LocalBinder) binder).getService();
         bound = true;         

         showMessage(dataService.getStatus());
      }

      public void onServiceDisconnected(ComponentName className){
         bound = false;         
      }
   };

   protected void onStart(){
      super.onStart();
      Intent intent = new Intent(this,NotesDataSyncService.class);
      bindService(intent,connection, Context.BIND_AUTO_CREATE);
   }

   protected void onStop(){
      super.onStop();
      if(bound){
         unbindService(connection);
      }
   }

}
