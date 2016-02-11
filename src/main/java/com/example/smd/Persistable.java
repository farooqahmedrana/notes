package com.example.smd;

import android.content.SharedPreferences;

public interface Persistable{
	
     public void save(SharedPreferences dataStore);
     public void load(SharedPreferences dataStore);
     public String getId();
     public String getType();
}
