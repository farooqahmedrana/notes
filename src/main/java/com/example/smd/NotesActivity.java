package com.example.smd;

import java.util.ArrayList;

import android.app.Activity;
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
    	
    	String text = "Note saved successfully";
    	Toast toast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
    	toast.show();
    }
    
    private void newNote(){
    	saveNote();
    	textArea.setText("");
    	currentNote = null;
    }
    
    private void listNotes(){
    	String text = "Total " + notes.size() + " notes";
    	Toast toast = Toast.makeText(this,text,Toast.LENGTH_LONG);
    	toast.show();
    }
}
