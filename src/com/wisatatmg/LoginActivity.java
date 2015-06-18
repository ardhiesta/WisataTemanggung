package com.wisatatmg;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wisatatmg.web.RequestMethod;
import com.wisatatmg.web.RestClient;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;



public class LoginActivity extends ActionBarActivity implements 
		SharedPreferences.OnSharedPreferenceChangeListener {
		SharedPreferences datauser;
		EditText user;
		EditText pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		datauser = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
	}
	@Override
	protected void onResume(){
		String username = datauser.getString("username", "");
		String password = datauser.getString("password", "");
		if (username.equals("")&&password.equals("")){
			
		}
		else{
			Intent intent = new Intent(LoginActivity.this, MenuUtamaActivity.class);
			startActivity(intent);
		}
		super.onResume();
	}
	public void registrasi(View view) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}
	 public void authenticateLogin (View view){
		 user = (EditText) findViewById(R.id.usernameET);
		 final String username = user.getText().toString();
		 pass = (EditText) findViewById(R.id.passwordET);
		 final String password = pass.getText().toString();
		 
		 RequestParams p = new RequestParams();
		 p.put("username", username);
		 p.put("password", password);
		 
		 final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
		 
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage("Proses Data...");
			dialog.setMax(100);
			dialog.setProgress(0);
			dialog.show();
		 
		 WisataRestClient.post("/login", p, new JsonHttpResponseHandler(){
			 @Override
			 public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
	                // If the response is JSONObject instead of expected JSONArray
				 dialog.cancel();
				 
				 try {
					String respon = response.getString("message");
					if (respon.equals("login sukses")){
					SharedPreferences.Editor editor = datauser.edit();
					editor.putString("username", username);
					editor.putString("password", password);
					editor.commit();
					
					user.setText("");
					pass.setText("");
					Intent intent = new Intent(LoginActivity.this, MenuUtamaActivity.class);
					startActivity(intent);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            }
		 });
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onBackPressed(){
		finish();
	}
}
