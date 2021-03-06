package com.wisatatmg;

import com.wisatatmg.web.RequestMethod;
import com.wisatatmg.web.RestClient;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity {
	SharedPreferences datauser;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datauser = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    }
    
    public void web(View view){
    	Intent intent = new Intent(this, WisataActivity.class);
    	startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.logout){
        	
        	
        	SharedPreferences.Editor editor = datauser.edit();
			
			editor.clear().commit();
        	Intent intent = new Intent(this, LoginActivity.class);
        	startActivity(intent);
        }
        
        return super.onOptionsItemSelected(item);
    }
}
