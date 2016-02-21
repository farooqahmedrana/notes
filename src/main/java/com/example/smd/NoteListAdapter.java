package com.example.smd;

import java.util.ArrayList;
import java.io.*;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.Toast;
import android.view.LayoutInflater;

public class NoteListAdapter extends ArrayAdapter<Note> implements Filterable
{
   private ArrayList<Note> notes;
   private ArrayList<Note> filteredNotes;
   private Filter filter;

   public NoteListAdapter(Context context,ArrayList<Note> notes){
      super(context,0,notes);
      this.notes = notes;
      this.filteredNotes = notes;
   }
   
   public Note getItem(int position){
	   return filteredNotes.get(position);
   }
      
	public int getCount() {
		return filteredNotes.size();
	}

   public View getView(int position, View convertView,ViewGroup parent) {
      Note note = getItem(position);
      	  
      if(convertView == null){
         LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = inflater.inflate(R.layout.note_list_item,parent,false);
      }

      TextView text = (TextView) convertView.findViewById(R.id.note_list_item_text);
      text.setText(note.getTitle());
      
      CheckBox check = (CheckBox) convertView.findViewById(R.id.note_list_item_check);
      check.setChecked(note.isImportant());

      Button button = (Button) convertView.findViewById(R.id.note_list_item_button);
      button.setTag(note);
      button.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            Note note = (Note) v.getTag();
            notes.remove(note);
            filteredNotes.remove(note);
            NotesDbHelper dbHelper = new NotesDbHelper(getContext());
            note.delete(dbHelper.getWritableDatabase());
            notifyDataSetChanged();

         }
      });

      return convertView;
   }
   
   @Override
	public Filter getFilter() {
	   if(filter == null){
		   filter = new NotesFilter();
	   }
	   return filter;
	}
   
   private class NotesFilter extends Filter{

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults results = new FilterResults();
		if(constraint != null && constraint.length() > 0){
			ArrayList<Note> filteredList = new ArrayList<Note>();
			for(int i=0; i < notes.size(); i++){
				if(notes.get(i).contains(constraint.toString())){
					filteredList.add(notes.get(i));
				}
			}
			
			results.count = filteredList.size();
			results.values = filteredList;
			
		}
		else{
			results.count = notes.size();
			results.values = notes;
		}
		
		return results;
	}

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		filteredNotes = (ArrayList<Note>) results.values;		
		notifyDataSetChanged();
	}
	   
   }
}
