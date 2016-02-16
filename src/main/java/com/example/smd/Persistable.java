package com.example.smd;

import java.io.OutputStream;
import java.io.InputStream;

public interface Persistable{
	
     public void save(OutputStream dataStore);
     public void load(InputStream dataStore);
     public String getId();
     public String getType();
}
