package com.wisatatmg;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.internal.mm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wisatatmg.web.RequestMethod;
import com.wisatatmg.web.RestClient;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class RuteActivity extends ActionBarActivity {
	
	private LocationManager locManager;
	private LocationListener locListener;
	GoogleMap mMap;
	MapDirection md;
	LatLng Tujuan;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rute);
		
		initLocationManager();
		
		String Lng = getIntent().getExtras().getString("Lng");
		String Lat = getIntent().getExtras().getString("Lat");
		Tujuan = new LatLng(Double.parseDouble(Lat),Double.parseDouble(Lng));
		
		md = new MapDirection();          
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.rute)).getMap();
		
		//
		
		//
		mMap.addMarker(new MarkerOptions().position(Tujuan).title("end.."));
		//
	}
	
	private void initLocationManager() {
			locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	
			locListener = new LocationListener() {
			//method ini akan dijalankan apabila koordinat GPS berubah
			public void onLocationChanged(Location newLocation) {	
			//tampilkanPosisikeMap(newLocation);
				LatLng myLokasi = new LatLng(newLocation.getLatitude(),newLocation.getLongitude());
				mMap.addMarker(new MarkerOptions().position(myLokasi).title("Start.."));
				
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLokasi, 13));
				getDirectionMap (myLokasi, Tujuan);
				}
			public void onProviderDisabled(String arg0) {
				}
			public void onProviderEnabled(String arg0) {
				}
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				}
			};
        	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
        	locListener);
		}
	
	
	private void getDirectionMap(LatLng from, LatLng to) {
		LatLng fromto[] = {from, to};
		new PostDirection().execute(fromto);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rute, menu);
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

	class PostDirection extends AsyncTask<LatLng, Void, Document>{
//		private ProgressDialog dialog;
//    	private Context appContext;
//  		String datawisata_postServices;
  		
  		
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
		protected Document doInBackground(LatLng... params) {
			Document doc = md.getDocument(params[0], params[1], MapDirection.MODE_DRIVING);
			return doc;
		}
		
		protected void onPostExecute(Document result) {
//			this.dialog.cancel();
			setRute(result);
		}

	}
	
	public void setRute(Document doc) {
		int duration = md.getDurationValue(doc);
		String distance = md.getDistanceText(doc);
		String start_addres = md.getStartAddress(doc);
		String copy_right = md.getCopyRights(doc);
		
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		PolylineOptions rectline = new PolylineOptions().width(3).color(Color.RED);
		
		System.out.println("direction point "+ directionPoint.size());
		
		for (int i =0; i < directionPoint.size(); i++) {
			rectline.add(directionPoint.get(i));
		}
		
		mMap.addPolyline(rectline);
	}
	
}
