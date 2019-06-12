package com.billydev.blib.model;

public class Event_Info {
	
	long event_id; 
	String dtappname; 
	
	public String getDtappname() {
		return dtappname;
	}


	public void setDtappname(String dtappname) {
		this.dtappname = dtappname;
	}


	public long getEvent_id() {
		return event_id;
	}


	public void setEvent_id(long event_id) {
		this.event_id = event_id;
	}
	String event_name; 
	
	
	public Event_Info() {
		
	}
	
	
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	

}
