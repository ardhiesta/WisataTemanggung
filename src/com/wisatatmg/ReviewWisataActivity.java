package com.wisatatmg;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ReviewWisataActivity extends ActionBarActivity {
	JSONObject tampil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_wisata);
		
		//mengambil data yang dikirim activity sebelumnya
		String dataWisata = getIntent().getExtras().getString("data-wisata"); 
        System.out.println("---> dataWisata: "+dataWisata);
        
        ImageView imgWisata = (ImageView) findViewById(R.id.imgReview);
        TextView txtDetailWisata = (TextView) findViewById(R.id.txtView2);
        TextView txtLongLat = (TextView) findViewById(R.id.txtView1);
        TextView txtNamaWisata = (TextView) findViewById(R.id.txtReview);
        
        try {
			tampil = new JSONObject(dataWisata);
			txtNamaWisata.setText(tampil.getString("nama_wisata"));
			txtDetailWisata.setText(tampil.getString("info_wisata"));
			txtLongLat.setText("Longitude : "+tampil.getString("long")+"\n"+"Latitude : "+tampil.getString("lat"));
			Picasso.with(ReviewWisataActivity.this)
			.load(Link.addres+"/latihan/img/" + tampil.getString("gambar_wisata"))
			.placeholder(R.drawable.ic_launcher)
			.error(R.drawable.ic_launcher)
			.into(imgWisata);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review_wisata, menu);
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
		else if (id == R.id.CariLokasi){
			Intent intent = new Intent(this, RuteActivity.class);
			try {
				intent.putExtra("Lng", tampil.getString("long"));
				intent.putExtra("Lat", tampil.getString("lat"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
