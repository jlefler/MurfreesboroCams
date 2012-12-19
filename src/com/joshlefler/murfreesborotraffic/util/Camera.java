package com.joshlefler.murfreesborotraffic.util;

import java.io.Serializable;

public class Camera implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Camera clone() {
		Camera c = new Camera();
		c.name = name;
		c.url = url;
		return c;
	}
}
