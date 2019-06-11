package com.billydev.blib.model;

public class APPTrig_Event_Info {
	
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
	String DT_Application_Name;
	
	
	public APPTrig_Event_Info() {
		
	}
	
	
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public String getDT_Application_Name() {
		return DT_Application_Name;
	}
	public void setDT_Application_Name(String dT_Application_Name) {
		this.DT_Application_Name = dT_Application_Name;
	} 
	

}
