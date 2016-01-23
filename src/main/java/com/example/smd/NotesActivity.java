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
        //setContentView(R.layout.main);
        createUi();
        notes = new ArrayList<Note>();
    }
    
    private void createUi(){
    	LinearLayout outerLayout = new LinearLayout(this);    	
    	outerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    	outerLayout.setOrientation(LinearLayout.VERTICAL);
    	
    	textArea = new EditText(this);    	
    	textArea.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1f));    	
     	textArea.setHint("Notes");
     	textArea.setGravity(Gravity.TOP);
    	
    	outerLayout.addView(textArea);
    	outerLayout.addView(createMenu());
    	setContentView(outerLayout);
    }
    
    private ViewGroup createMenu(){
    	LinearLayout layout = new LinearLayout(this);    	
    	layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    	layout.setOrientation(LinearLayout.HORIZONTAL);    	
    	layout.setGravity(Gravity.CENTER);
    	    	
    	LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
     	
     	Button saveButton = new Button(this);     	
    	saveButton.setLayoutParams(params);
    	saveButton.setText("Save");
    	saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				saveNote();
			}
		});
    	
    	Button newButton = new Button(this);     	
    	newButton.setLayoutParams(params);
    	newButton.setText("New");
    	newButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				newNote();				
			}
		});
    	
    	Button listButton = new Button(this);     	
    	listButton.setLayoutParams(params);
    	listButton.setText("List");
    	listButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				listNotes();				
			}
		});
    	
    	layout.addView(saveButton);
    	layout.addView(newButton);
    	layout.addView(listButton);
    	
    	return layout;    	
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
