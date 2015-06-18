package com.wisatatmg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.wisatatmg.web.RequestMethod;
import com.wisatatmg.web.RestClient;

import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts.Intents;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WisataActivity extends ActionBarActivity {
	ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wisata);
		setTitle("Wisata");
		listview = (ListView) findViewById(R.id.listView1);
		
		final ProgressDialog dialog = new ProgressDialog(WisataActivity.this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Proses Data...");
		dialog.setMax(100);
		dialog.setProgress(0);
		dialog.show();
		
		WisataRestClient.get("/wisata", null, new JsonHttpResponseHandler(){
			@Override
			 public void onSuccess(int statusCode, Header[] headers, JSONArray response){
				buatTampilListView(response);
				dialog.cancel();
			}
		});
	}
	
	class StableArrayAdapter extends ArrayAdapter<JSONObject>{
		Context myContext;
		ArrayList<JSONObject> alWisata;
		
//		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
		public StableArrayAdapter(Context context, int resource, ArrayList objects) {
			super(context, resource, objects);
			myContext = context;
			alWisata =  objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			LayoutInflater inflater = LayoutInflater.from(WisataActivity.this);
			View rootView = inflater.inflate(R.layout.wisata_row, null);
			
			TextView txtNamaWisata = (TextView) rootView.findViewById(R.id.txtNamaWisata);
			TextView txtDetailWisata = (TextView) rootView.findViewById(R.id.txtDetailWisata);
			ImageView imgWisata = (ImageView) rootView.findViewById(R.id.foto_wisata);
			
			try {
				String img = Link.addres+"/latihan/img/"+alWisata.get(position).getString("gambar_wisata");
				txtNamaWisata.setText(alWisata.get(position).getString("nama_wisata"));
				txtDetailWisata.setText(alWisata.get(position).getString("info_wisata").substring(0, 20));
				Picasso.with(myContext)
					.load(img)
					.placeholder(R.drawable.ic_launcher)
					.error(R.drawable.ic_launcher)
					.into(imgWisata);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return rootView;
		}
	}
	
	private void buatTampilListView(JSONArray wisata){
		final ArrayList<JSONObject> alWisata = new ArrayList<JSONObject>();
		try {
//			JSONArray wisata = new JSONArray(dataWisata);
			System.out.println("---> wisata length : "+wisata.length());
			for (int i=0; i<wisata.length(); i++){
				JSONObject wisata1 = wisata.getJSONObject(i);
				alWisata.add(wisata1);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.wisata_row, alWisata);
		System.out.println("----------> Set Adapter");
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent detail = new Intent(WisataActivity.this, ReviewWisataActivity.class);
				//mengirim data ke activity ReviewWisataActivity
				detail.putExtra("data-wisata", alWisata.get(position).toString()); 
				startActivity(detail);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wisata, menu);
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
