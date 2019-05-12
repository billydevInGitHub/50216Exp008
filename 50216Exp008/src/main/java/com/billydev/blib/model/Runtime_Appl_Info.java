package com.billydev.blib.model;

import java.util.ArrayList;

public class Runtime_Appl_Info {
	/*
	 * the following the diagram part
	 */
	ArrayList<Diagram_Rect_Info> datasetForRect = new ArrayList<>(); 
	
	ArrayList<Diagram_Line_Info> datasetForLine = new ArrayList<>(); 
	
	long generationNumber ; 
	
	/*
	 * the following is job info
	 * we can add this when needed 
	 */

	ArrayList<RT_Job_Info> jobs= new ArrayList<>(); 
	
	
	public long getGenerationNumber() {
		return generationNumber;
	}

	public void setGenerationNumber(long generationNumber) {
		this.generationNumber = generationNumber;
	}

	public ArrayList<RT_Job_Info> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<RT_Job_Info> jobs) {
		this.jobs = jobs;
	}

	/*
	 * the following is appl info
	 */
	String applicationName; 
	String DT_ApplicationName; 
	String state; 
	
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getDT_ApplicationName() {
		return DT_ApplicationName;
	}

	public void setDT_ApplicationName(String dT_ApplicationName) {
		DT_ApplicationName = dT_ApplicationName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Runtime_Appl_Info() {
		
	}
	
//	public void addRect(Diagram_Rect_Info  rectInfo) {
//		datasetForRect.add(rectInfo); 
//	}
//	
	
	public ArrayList<Diagram_Rect_Info> getDatasetForRect() {
		return datasetForRect;
	}

	public void setDatasetForRect(ArrayList<Diagram_Rect_Info> datasetForRect) {
		this.datasetForRect = datasetForRect;
	}

	public ArrayList<Diagram_Line_Info> getDatasetForLine() {
		return datasetForLine;
	}

	public void setDatasetForLine(ArrayList<Diagram_Line_Info> datasetForLine) {
		this.datasetForLine = datasetForLine;
	}

//	public void addLine(Diagram_Line_Info lineInfo) {
//		datasetForLine.add(lineInfo); 
//	}

}
