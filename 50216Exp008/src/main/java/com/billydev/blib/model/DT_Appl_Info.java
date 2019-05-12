package com.billydev.blib.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class DT_Appl_Info {

	ArrayList<DT_Job_Info>  jobList = new ArrayList<>(); 
	
	
	public ArrayList<DT_Job_Info> getJobList() {
		return jobList;
	}

	public void setJobList(ArrayList<DT_Job_Info> jobList) {
		this.jobList = jobList;
	}
	String appName; 
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	String applscript;
	Timestamp uploadTime ; 
	String appCreateUserId; 
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	long appl_id; 
	
	
	public long getAppl_id() {
		return appl_id;
	}

	public String getAppCreateUserId() {
		return appCreateUserId;
	}

	public void setAppCreateUserId(String appCreateUserId) {
		this.appCreateUserId = appCreateUserId;
	}

	public void setAppl_id(long appl_id) {
		this.appl_id = appl_id;
	}





	public DT_Appl_Info() {
		
	}
	

	public String getApplscript() {
		return applscript;
	}
	public void setApplscript(String applscript) {
		this.applscript = applscript;
	} 
	
}
