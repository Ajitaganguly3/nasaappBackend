package com.nasaapp.apod.entity;

public class Apod {

	private String copyright;
	private String title;
	private String date;
	private String explanation;
	private String hdurl;
	private String media_type;
	private String service_version;
	private String url;

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getHdurl() {
		return hdurl;
	}

	public void setHdurl(String hdurl) {
		this.hdurl = hdurl;
	}

	public String getMedia_type() {
		return media_type;
	}

	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}

	public String getService_version() {
		return service_version;
	}

	public void setService_version(String service_version) {
		this.service_version = service_version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Apod() {

	}

	public Apod(String copyright, String title, String date, String explanation, String hdurl, String media_type,
			String service_version, String url) {
		super();
		this.copyright = copyright;
		this.title = title;
		this.date = date;
		this.explanation = explanation;
		this.hdurl = hdurl;
		this.media_type = media_type;
		this.service_version = service_version;
		this.url = url;
	}

}