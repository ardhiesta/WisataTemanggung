package com.wisatatmg;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckedTextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private final SparseArray<Group> groups;
	public LayoutInflater inflater;
	public Activity activity;

	public MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
		activity = act;
		this.groups = groups;
		inflater = act.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).children.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final JSONObject children = (JSONObject) getChild(groupPosition,
				childPosition);
		ImageView img = null;
		TextView nama = null;
		TextView detail = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_details, null);
		}
		img = (ImageView) convertView.findViewById(R.id.foto_wisata);
		nama = (TextView) convertView.findViewById(R.id.txtNamaWisata);
		detail = (TextView) convertView.findViewById(R.id.txtDetailWisata);

		try {
			String pic = Link.addres + "/latihan/img/"
					+ children.getString("gambar_wisata");
			Picasso.with(activity).load(pic)
					.placeholder(R.drawable.ic_launcher)
					.error(R.drawable.ic_launcher).into(img);
			nama.setText(children.getString("nama_wisata"));
			detail.setText(children.getString("info_wisata").substring(0, 3));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent detail = new Intent(activity, ReviewWisataActivity.class);
				// mengirim data ke activity ReviewWisataActivity
				detail.putExtra("data-wisata", children.toString());
				activity.startActivity(detail);
			}
		});

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_group, null);
		}
		Group group = (Group) getGroup(groupPosition);
		((CheckedTextView) convertView).setText(group.string);
		((CheckedTextView) convertView).setChecked(isExpanded);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
