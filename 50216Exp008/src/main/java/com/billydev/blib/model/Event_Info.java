package com.billydev.blib.model;

import java.sql.Timestamp;

public class Event_Info {
	
	long event_id; 
	String dtappname; 
	Timestamp next_scheduled_time; 
	String eventCreatorUserId; 
	
	public String getDtappname() {
		return dtappname;
	}


	public String getEventCreatorUserId() {
		return eventCreatorUserId;
	}


	public void setEventCreatorUserId(String eventCreatorUserId) {
		this.eventCreatorUserId = eventCreatorUserId;
	}


	public Timestamp getNext_scheduled_time() {
		return next_scheduled_time;
	}


	public void setNext_scheduled_time(Timestamp next_scheduled_time) {
		this.next_scheduled_time = next_scheduled_time;
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
