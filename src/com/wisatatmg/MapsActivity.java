package com.wisatatmg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wisatatmg.web.RequestMethod;
import com.wisatatmg.web.RestClient;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapsActivity extends ActionBarActivity {
//	String datawisata_postServices = " ";
	
	protected GoogleMap map = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		 
		 
		 setUpMapIfNeeded();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// TODO Auto-generated method stub
		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();
			if (map != null){
				PostServices panggil = new PostServices();
//				 panggil.appContext = MapsActivity.this; 
				 panggil.execute(); 
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maps, menu);
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
	
	class PostServices extends AsyncTask<String, String, JSONArray> {
		
//    	private ProgressDialog dialog;
//    	private Context appContext;
  		
  		
  		protected void onPreExecute() {
//    		dialog = new ProgressDialog(appContext);
//    		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//    		dialog.setTitle("menunggu");
//    		dialog.setMessage("memposting");
//    		dialog.setMax(100);
//    		dialog.setProgress(0);
//    		dialog.show();
    	}
		@Override
		protected JSONArray doInBackground(String... params) {
			// TODO Auto-generated method stub
			String link = Link.url+"/wisata";
	        RestClient client = new RestClient(link);
	        JSONArray datawisata_postServices = null;
	        try {
				client.execute(RequestMethod.GET);
				System.out.println(client.getResponse());
				datawisata_postServices = new JSONArray(client.getResponse());
				client.getResponse();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return datawisata_postServices;
		}
    	
		protected void onPostExecute(JSONArray wisata) {
//			this.dialog.cancel();
			
			System.out.println("Panjang Array :"+wisata.length());
			Toast.makeText(MapsActivity.this, "Panjang Array :"+wisata.length(), Toast.LENGTH_LONG).show();
			try {
				for (int i=0; i<wisata.length(); i++){
					JSONObject wisata1 = wisata.getJSONObject(i);
					Double lng = Double.parseDouble(wisata1.getString("long")) ;
					Double lat = Double.parseDouble(wisata1.getString("lat")) ;
					        
			        map.addMarker(new MarkerOptions()
					        .title(wisata1.getString("nama_wisata"))
					        .snippet("Ini "+wisata1.getString("nama_wisata"))
					        .position(new LatLng(lat, lng)));
				}
				LatLng tmg = new LatLng(-7.3183031, 110.1815996);
		        map.setMyLocationEnabled(true);
		        map.animateCamera(CameraUpdateFactory.newLatLngZoom(tmg, 10));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
//	@Override
//	public void onMapReady(GoogleMap map) {
//		LatLng tmg = new LatLng(-7.3183031, 110.1815996);
//
//        map.setMyLocationEnabled(true);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(tmg, 10));
//
//       
//
////        try {
////			JSONArray wisata = new JSONArray(datawisata_postServices);
////			for (int i=0; i<wisata.length(); i++){
////				JSONObject wisata1 = wisata.getJSONObject(i);
////				Double lng = Double.parseDouble(wisata1.getString("long")) ;
////				Double lat = Double.parseDouble(wisata1.getString("lat")) ;
////				
////				LatLng pikatan = new LatLng(lat,lng);
////		        map.addMarker(new MarkerOptions()
////				        .title(wisata1.getString("nama_wisata"))
////				        .snippet("Ini "+wisata1.getString("nama_wisata"))
////				        .position(pikatan));
////			}
////		} catch (JSONException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////        LatLng pikatan = new LatLng(-7.338305, 110.184923);
////        map.addMarker(new MarkerOptions()
////		        .title("Pikatan")
////		        .snippet("Ini Pikatan")
////		        .position(pikatan));
////        
////        LatLng posong = new LatLng(-7.3287356, 110.0200725);
////        map.addMarker(new MarkerOptions()
////		        .title("Posong")
////		        .snippet("Ini Posong")
////		        .position(posong));
////        
////        LatLng jumprit = new LatLng(-7.2383352, 110.0583861);
////        map.addMarker(new MarkerOptions()
////		        .title("Jumprit")
////		        .snippet("Ini Jumprit")
////		        .position(jumprit));
//	}
}
