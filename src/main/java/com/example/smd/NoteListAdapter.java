package com.example.smd;

import java.util.ArrayList;
import java.io.*;

import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.Toast;
import android.view.LayoutInflater;

public class NoteListAdapter extends ArrayAdapter<Note>
{
   private ArrayList<Note> notes;

   public NoteListAdapter(Context context,ArrayList<Note> notes){
      super(context,0,notes);
      this.notes = notes;
   }

   public View getView(int position, View convertView,ViewGroup parent) {
      Note note = getItem(position);
      if(convertView == null){
         LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = inflater.inflate(R.layout.note_list_item,parent,false);
      }

      TextView text = (TextView) convertView.findViewById(R.id.note_list_item_text);
      text.setText(note.getContent());
      
      CheckBox check = (CheckBox) convertView.findViewById(R.id.note_list_item_check);
      check.setChecked(note.isImportant());

      Button button = (Button) convertView.findViewById(R.id.note_list_item_button);
      button.setTag(position);
      button.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            Integer index = (Integer) v.getTag();
            notes.remove(index.intValue());  
            notifyDataSetChanged();

         }
      });

      return convertView;
   }
}
