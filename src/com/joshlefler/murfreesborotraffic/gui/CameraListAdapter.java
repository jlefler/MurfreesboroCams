package com.joshlefler.murfreesborotraffic.gui;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joshlefler.murfreesborotraffic.R;
import com.joshlefler.murfreesborotraffic.util.Camera;

public class CameraListAdapter extends BaseAdapter {

	private ArrayList<Camera> mCameras;
	private LayoutInflater mInflater;

	public CameraListAdapter(Context context, ArrayList<Camera> cameras) {
		mCameras = cameras;
		mInflater = LayoutInflater.from(context);
	}
	public int getCount() {
		return mCameras.size();
	}

	public Camera getItem(int position) {
		return mCameras.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Camera camera = mCameras.get(position);
		ViewHolder vh;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.camera_row, parent, false);
			vh = new ViewHolder();
			vh.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		vh.title.setText(camera.getName());

		return convertView;
	}

	class ViewHolder {
		TextView title;
	}
}
