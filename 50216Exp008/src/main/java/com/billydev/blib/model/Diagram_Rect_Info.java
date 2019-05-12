package com.billydev.blib.model;

public class Diagram_Rect_Info {
	
	
	int x;
	int y;
	int width;
	int height;
	int x_job_name; 
	int y_job_name; 
	String job_name;
	String state ; 
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Diagram_Rect_Info() {
		
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getX_job_name() {
		return x_job_name;
	}
	public void setX_job_name(int x_job_name) {
		this.x_job_name = x_job_name;
	}
	public int getY_job_name() {
		return y_job_name;
	}
	public void setY_job_name(int y_job_name) {
		this.y_job_name = y_job_name;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	} 
	

}
