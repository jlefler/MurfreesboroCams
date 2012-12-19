package com.joshlefler.murfreesborotraffic;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.joshlefler.murfreesborotraffic.gui.MjpegInputStream;
import com.joshlefler.murfreesborotraffic.gui.MjpegView;
import com.joshlefler.murfreesborotraffic.util.Camera;

public class SingleCamera extends Activity {
	 private static final String TAG = "MjpegActivity";

	    private MjpegView mv;

		private Camera mCamera;

	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mCamera = (Camera)getIntent().getSerializableExtra("camera");

	        //sample public cam
	        String URL = mCamera.getUrl(); 

	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	        WindowManager.LayoutParams.FLAG_FULLSCREEN);

	        mv = new MjpegView(this);
	        setContentView(mv);        

	        new DoRead().execute(URL);
	    }

	    public void onPause() {
	        super.onPause();
	        mv.stopPlayback();
	    }

	    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
	        protected MjpegInputStream doInBackground(String... url) {
	            //TODO: if camera has authentication deal with it and don't just not work
	            HttpResponse res = null;
	            DefaultHttpClient httpclient = new DefaultHttpClient();     
	            Log.d(TAG, "1. Sending http request");
	            try {
	                res = httpclient.execute(new HttpGet(URI.create(url[0])));
	                Log.d(TAG, "2. Request finished, status = " + res.getStatusLine().getStatusCode());
	                if(res.getStatusLine().getStatusCode() != 200){
	                    return null;
	                }
	                return new MjpegInputStream(res.getEntity().getContent());  
	            } catch (ClientProtocolException e) {
	                e.printStackTrace();
	                Log.d(TAG, "Request failed-ClientProtocolException", e);
	                //Error connecting to camera
	            } catch (IOException e) {
	                e.printStackTrace();
	                Log.d(TAG, "Request failed-IOException", e);
	                //Error connecting to camera
	            }

	            return null;
	        }

	        protected void onPostExecute(MjpegInputStream result) {
	        	if (result == null) {
	        		Toast.makeText(SingleCamera.this, "The camera returned an error.", Toast.LENGTH_SHORT).show();
	        	} else {
		            mv.setSource(result);
		            mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
		            mv.showFps(true);
	        	}
	        }
	    }
}
