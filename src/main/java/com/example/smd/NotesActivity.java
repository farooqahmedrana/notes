package com.example.smd;

import java.util.ArrayList;
import java.io.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class NotesActivity extends Activity
{
	ArrayList<Note> notes;
	Note currentNote;
	
	EditText textArea;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textArea = (EditText) findViewById(R.id.text_area);
        notes = new ArrayList<Note>();

 	   showMessage("Create");
    }

    public void onStart(){
        super.onStart();
        showMessage("Start");
    }

    public void onResume(){
        super.onResume();
        showMessage("Resume");
    }

    public void onPause(){
        super.onPause();
        showMessage("Pause");
    }

    public void onStop(){
        super.onStop();
        showMessage("Stop");
    }

    public void onDestroy(){
        super.onDestroy();
        showMessage("Destroy");
    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        try{
           savedInstanceState.putSerializable("noteslist",notes);
        }
        catch(Exception ex){ }         

        showMessage("SaveInstanceState");
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        try{
           notes = (ArrayList<Note>) savedInstanceState.getSerializable("noteslist");
        }
        catch(Exception ex){ }

        showMessage("RestoreInstanceState");
    }
    
    public void buttonClick(View v){

        if(v.getId() == R.id.button_save){
           saveNote();
        }

        if(v.getId() == R.id.button_new){
           newNote();
        }

        if(v.getId() == R.id.button_list){
           listNotes();
        }

    }
    
    private void saveNote(){
    	   String content = textArea.getText().toString();

    	   if(currentNote == null){
    		 currentNote = new Note(content);
    		 notes.add(currentNote);
    	   }

    	   currentNote.setContent(content);
    	
        showMessage("Note saved successfully");
    }
    
    private void newNote(){
    	   saveNote();
    	   textArea.setText("");
    	   currentNote = null;
    }
     
    private void listNotes(){
        //showMessage("Total " + notes.size() + " notes");
    	Intent intent = new Intent(this,ListActivity.class);
    	intent.putExtra("list",notes);
    	startActivity(intent);
    }

    private void showMessage(String message){
        Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        toast.show();
    }
}
