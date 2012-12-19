package com.joshlefler.murfreesborotraffic;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.joshlefler.murfreesborotraffic.gui.CameraListAdapter;
import com.joshlefler.murfreesborotraffic.util.Camera;
import com.joshlefler.murfreesborotraffic.util.ConfigParser;

public class CameraList extends ListActivity {

	private ArrayList<Camera> mCameras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCameras = ConfigParser.parse(getApplicationContext());
		CameraListAdapter adapter = new CameraListAdapter(this, mCameras);
		getListView().setAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Camera camera = mCameras.get(position);
		Intent intent = new Intent(CameraList.this, SingleCamera.class);
		intent.putExtra("camera", camera);
		startActivity(intent);
	}
	
}
