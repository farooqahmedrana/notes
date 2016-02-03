package com.example.smd;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       	MenuInflater inflater = getMenuInflater();
       	inflater.inflate(R.menu.main_menu, menu);
       	return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.new_menu:
				newMenu();
				return true;
			case R.id.list_menu:
				listMenu();
				return true;
			case android.R.id.home:
				onBackPressed();
				return true;
			default: 
				return super.onOptionsItemSelected(item); 
		}
	}
	
	protected abstract void newMenu();
	protected abstract void listMenu();
	

}
