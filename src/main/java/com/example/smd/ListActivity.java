package com.example.smd;

import java.util.ArrayList;
import java.io.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends BaseActivity
{
	ArrayList<Note> notes;
	int selectedItem;
	
	EditText text;
	ListView list;
	NoteListAdapter adapter;
	
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
    
    private EditText createText(){
    	text = new EditText(this);
    	text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    	text.setHint("Filter");
    	text.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) { }

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) { }

			@Override
			public void onTextChanged(CharSequence text, int start, int before,int count) {
				adapter.getFilter().filter(text.toString());
			}
    		
    	});

    	return text;
    }
    
    private ListView createList(){
    	list = new ListView(this);
    	list.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    	    	
        adapter = new NoteListAdapter(this,notes);
    	list.setAdapter(adapter);    	
    	
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
    			openNote(position);
    		}
		});
    	
    	registerForContextMenu(list);
    	
    	return list;
    }
    
    private void createView(){
    	LinearLayout layout = new LinearLayout(this);
    	layout.setOrientation(LinearLayout.VERTICAL);
    	layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    	layout.setFocusable(true);
    	layout.setFocusableInTouchMode(true);
    	
    	
    	layout.addView(createText());
    	layout.addView(createList());
    	
    	setContentView(layout);
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
    
    @Override
    protected void newMenu() {
    	prepareResult();
    }
    
    @Override
    protected void listMenu() {
        // do nothing
    }
    
    private void openNote(int position){
    	selectedItem = position;
		prepareResult();
		finish();
    }
    
    private void deleteNote(int position){
    	notes.remove(position);
    	adapter.notifyDataSetChanged();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.context_menu, menu);
    }
    
    
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	
    	switch(item.getItemId()){
    		case R.id.open_item_menu:
    			openNote(info.position);
    			return true;
    		case R.id.delete_item_menu:
    			deleteNote(info.position);
    			return true;
    		default:
    			return super.onContextItemSelected(item);
    	}
       	
    }
        
}
