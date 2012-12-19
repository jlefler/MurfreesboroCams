package com.joshlefler.murfreesborotraffic.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import android.content.Context;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

public class ConfigParser {
	public static ArrayList<Camera> parse(Context context) {
		final ArrayList<Camera> cameras = new ArrayList<Camera>();
		final Camera currentCamera = new Camera();
		
		RootElement root = new RootElement("cameras");
		
		Element camera = root.getChild("camera");
		
		camera.setStartElementListener(new StartElementListener() {
			
			public void start(Attributes attributes) {
				currentCamera.setName(attributes.getValue("name"));
				currentCamera.setUrl(attributes.getValue("url"));
			}
		});
		
		camera.setEndElementListener(new EndElementListener() {
			
			public void end() {
				cameras.add(currentCamera.clone());
			}
		});
		
		try {
			InputStream is = context.getAssets().open("config.xml");
			String config = stringFromInputStream(is);
			Xml.parse(config, root.getContentHandler());
		} catch (IOException ioe) {
			Log.e("PD", "Error reading config file", ioe);
		} catch (SAXException sae) {
			Log.e("PD", "Error parsing config file", sae);
		}
		
		return cameras;
	}
	
	public static String stringFromInputStream (InputStream in) throws IOException {
	    StringBuffer out = new StringBuffer();
	    byte[] b = new byte[4096];
	    for (int n; (n = in.read(b)) != -1;) {
	        out.append(new String(b, 0, n));
	    }
	    return out.toString();
	}
}
