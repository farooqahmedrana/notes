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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity
{
	ArrayList<Note> notes;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        notes = (ArrayList<Note>) intent.getSerializableExtra("list");
        
        if(notes == null){
        	notes = new ArrayList<Note>();
        }
        
        createView();
    }
    
    private void createView(){
    	ListView view = new ListView(this);
    	view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    	String [] array = new String[notes.size()];
    	for(int i=0; i < notes.size(); i++){
    		array[i] = notes.get(i).getContent();
    	}

     NoteListAdapter adapter = new NoteListAdapter(this,notes);
    	view.setAdapter(adapter);    	
    	setContentView(view);
    }

}
