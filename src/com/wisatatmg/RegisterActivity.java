package com.wisatatmg;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity {

	EditText reguser;
	 EditText regpass;
	 EditText regemail; 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		registerViews();
	}
	
	private void registerViews(){
		reguser = (EditText) findViewById(R.id.reguserET);
		regpass = (EditText) findViewById(R.id.regpassET);
		regemail = (EditText) findViewById(R.id.regemailET);
		 
		 
		reguser.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}		
			@Override
			public void afterTextChanged(Editable s) {
				harusDiisi(reguser);
				// TODO Auto-generated method stub
			}
		});
		
		regpass.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void afterTextChanged(Editable s) {
				harusDiisi(regpass);
				// TODO Auto-generated method stub	
			}
		});
	}
	
	private boolean checkValidation(){
		boolean ret = true;

		if (!harusDiisi(reguser)&&!harusDiisi(regpass))
			ret = false;

		return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	public void daftar (View view){
		 final String username = reguser.getText().toString();
		 final String password = regpass.getText().toString();
		 final String email = regemail.getText().toString();
		 
		 RequestParams p = new RequestParams();
		 p.put("username", username);
		 p.put("password", password);
		 p.put("email", email);
		 
		 if (checkValidation()){
			 final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.setMessage("Proses Data...");
				dialog.setMax(100);
				dialog.setProgress(0);
				dialog.show();
				
				WisataRestClient.post("/register", p, new JsonHttpResponseHandler(){
				 @Override
				 public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		                // If the response is JSONObject instead of expected JSONArray
					 dialog.cancel();
					 try {
						String respon = response.getString("message");
						if (respon.equals("insert sukses")){
							AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
							builder.setMessage("Register sukses, Silahkan Login dengan akun anda")
							.setTitle("Konfirmasi").setPositiveButton("OK", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								finish();
								}
							});
							AlertDialog dialog = builder.create();
							dialog.show();
						}
						else {
							AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
							builder.setMessage("Registrasi gagal, username sudah digunakan")
							.setTitle("Konfirmasi").setPositiveButton("OK", new OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								}
							});
							AlertDialog dialog = builder.create();
							dialog.show();
						}
					 } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 	}
		              }
					});
		 }else {
			 Toast.makeText(RegisterActivity.this, "Registrasi Error",
						Toast.LENGTH_LONG).show();
		 } 
	}
		
	public boolean harusDiisi(EditText editText){
		String text = editText.getText().toString().trim();
		editText.setError(null);
		
		//length 0 artinya teks kosong
		if (text.length()==0){
			editText.setError(Html.fromHtml("<font color='red'>Input tidak boleh kosong</font>"));
			return false;
		}
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
}
