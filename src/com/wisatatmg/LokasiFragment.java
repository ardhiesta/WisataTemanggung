package com.wisatatmg;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.wisatatmg.WisataActivity.StableArrayAdapter;
import com.wisatatmg.web.RequestMethod;
import com.wisatatmg.web.RestClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LokasiFragment extends Fragment {
	ListView listview;
	 @Override
	    public View onCreateView(LayoutInflater inflater, 
	    						 ViewGroup container, 
	    						 Bundle savedInstanceState){
	        final View rootView = inflater.inflate(R.layout.fragment_lokasi, container, false);
	        listview = (ListView)rootView.findViewById(R.id.listView1);
	        
			final ProgressDialog dialog = new ProgressDialog(getActivity());
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
			return rootView;
	    }
	 
		class StableArrayAdapter extends ArrayAdapter<JSONObject>{
			Context myContext;
			ArrayList<JSONObject> alWisata;
			
//			HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
			public StableArrayAdapter(Context context, int resource, ArrayList objects) {
				super(context, resource, objects);
				myContext = context;
				alWisata =  objects;
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent){
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				View rootView = inflater.inflate(R.layout.wisata_row, null);
				
				TextView txtNamaWisata = (TextView) rootView.findViewById(R.id.txtNamaWisata);
				TextView txtDetailWisata = (TextView) rootView.findViewById(R.id.txtDetailWisata);
				ImageView imgWisata = (ImageView) rootView.findViewById(R.id.foto_wisata);
				
				try {
					String img = Link.addres+"/latihan/img/"+alWisata.get(position).getString("gambar_wisata");
					txtNamaWisata.setText(alWisata.get(position).getString("nama_wisata"));
					txtDetailWisata.setText(alWisata.get(position).getString("info_wisata").substring(0, 3));
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
//				JSONArray wisata = new JSONArray(dataWisata);
				System.out.println("---> wisata length : "+wisata.length());
				for (int i=0; i<wisata.length(); i++){
					JSONObject wisata1 = wisata.getJSONObject(i);
					alWisata.add(wisata1);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StableArrayAdapter adapter = new StableArrayAdapter(getActivity(), R.layout.wisata_row, alWisata);
			System.out.println("----------> Set Adapter");
			listview.setAdapter(adapter);
			
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					Intent detail = new Intent(getActivity(), ReviewWisataActivity.class);
					//mengirim data ke activity ReviewWisataActivity
					detail.putExtra("data-wisata", alWisata.get(position).toString()); 
					startActivity(detail);
				}
			});
		}
}
