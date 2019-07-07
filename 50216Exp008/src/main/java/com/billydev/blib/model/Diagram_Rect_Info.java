package com.billydev.blib.model;

import java.sql.Timestamp;

public class Diagram_Rect_Info {
	
	
	int x;
	int y;
	int width;
	int height;
	int x_job_name; 
	int y_job_name; 
	String job_name;
	String state ; 
	String application_name;
	long appl_generation_number;
	String predecessor_names;
	String successor_names;
	Timestamp start_time;
	Timestamp end_time;
	String agent_name;
	String script;
	String arguments_of_script;
	public String getApplication_name() {
		return application_name;
	}

	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}

	public long getAppl_generation_number() {
		return appl_generation_number;
	}

	public void setAppl_generation_number(long appl_generation_number) {
		this.appl_generation_number = appl_generation_number;
	}

	public String getPredecessor_names() {
		return predecessor_names;
	}

	public void setPredecessor_names(String predecessor_names) {
		this.predecessor_names = predecessor_names;
	}

	public String getSuccessor_names() {
		return successor_names;
	}

	public void setSuccessor_names(String successor_names) {
		this.successor_names = successor_names;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getArguments_of_script() {
		return arguments_of_script;
	}

	public void setArguments_of_script(String arguments_of_script) {
		this.arguments_of_script = arguments_of_script;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getShell() {
		return shell;
	}

	public void setShell(String shell) {
		this.shell = shell;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	String userid;
	String shell; 
	String return_code;
	String description; 
	
	
	
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
