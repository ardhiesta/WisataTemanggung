package com.wisatatmg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter.LengthFilter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class KategoriFragment extends Fragment {
	SparseArray<Group> groups = new SparseArray<Group>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_kategori,
				container, false);
		createData();
		ExpandableListView listView = (ExpandableListView) rootView
				.findViewById(R.id.listView);
		MyExpandableListAdapter adapter = new MyExpandableListAdapter(
				getActivity(), groups);
		listView.setAdapter(adapter);

		return rootView;
	}

	private void createData() {
		
		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Proses Data...");
		dialog.setMax(100);
		dialog.setProgress(0);
		dialog.show();

		WisataRestClient.get("/wisata", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray response) {
				
				List<String> nama_kategori = new ArrayList<String>();
				for (int i = 0; i < response.length(); i++) {
//					Toast.makeText(getActivity(), "test...", Toast.LENGTH_LONG).show();
					try {
						JSONObject object = (JSONObject) response.get(i);
//						Toast.makeText(getActivity(),"nama_kategori : " + object.getString("nama_kategori"),Toast.LENGTH_LONG).show();
						nama_kategori.add(object.getString("nama_kategori"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Set<String> unik_kategori = new HashSet<String>(nama_kategori);
				List<String> list_unik_kategori = new ArrayList<String>(
						unik_kategori);

				for (int j = 0; j < list_unik_kategori.size(); j++) {
					String unik = list_unik_kategori.get(j);
					Group group = new Group(unik);
					for (int i = 0; i < response.length(); i++) {

						try {
							JSONObject object = (JSONObject) response.get(i);
							if (object.getString("nama_kategori").equals(unik)) {
								group.children.add(object);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				groups.append(j, group);
			}
				dialog.cancel();
			}
		});
	}

}
