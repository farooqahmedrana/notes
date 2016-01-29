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
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.Toast;

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
         LinearLayout layout = new LinearLayout(getContext());
         layout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         layout.setOrientation(LinearLayout.HORIZONTAL);

         TextView text = new TextView(getContext());
         text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT,1f));
         text.setId(1);
         layout.addView(text);

         Button button = new Button(getContext());
         button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
         button.setId(2);
         layout.addView(button);

         convertView = layout;
      }

      TextView text = (TextView) convertView.findViewById(1);
      text.setText(note.getContent());

      Button button = (Button) convertView.findViewById(2);
      button.setText("X");
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
