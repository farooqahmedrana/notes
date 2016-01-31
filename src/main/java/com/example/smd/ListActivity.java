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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	int selectedItem;
	
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
        
        selectedItem = -1;        
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
    	
    	view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
    			selectedItem = position;
    			prepareResult();
    			finish();
    		}
		});
    	
    	setContentView(view);
		
    }
    
    private void prepareResult(){
    	Intent intent = new Intent();
    	intent.putExtra("list", notes);
    	intent.putExtra("selecteditemindex", selectedItem);
    	setResult(RESULT_OK, intent);
    }
    
    @Override
    public void onBackPressed() {
    	prepareResult();
    	super.onBackPressed();
    }
    
    
}
