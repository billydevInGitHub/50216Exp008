package com.billydev.blib.model;

import java.util.ArrayList;

public class RT_Job_Info {
	long job_id; 
	String job_name;
	String qualifier; 
	String user_id; 
	String job_type; 
	String predecessor_names; 
	String target; 
	int port; 
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSuccessor_names(String successor_names) {
		this.successor_names = successor_names;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}


	public long getAppl_generation_number() {
		return appl_generation_number;
	}

	public void setAppl_generation_number(long appl_generation_number) {
		this.appl_generation_number = appl_generation_number;
	}


	String successor_names; 
	long appl_generation_number; 
	ArrayList<RT_Job_Info> predecessors; 
	ArrayList<RT_Job_Info> successors; 

	public String getPredecessor_names() {
		return predecessor_names;
	}

	public void setPredecessor_names(String predecessor_names) {
		this.predecessor_names = predecessor_names;
	}

	public String getSuccessor_names() {
		return successor_names;
	}

	public void setSuccessor_ids(String successor_ids) {
		this.successor_names = successor_ids;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	String agent_name; 
	String arguments_of_script; 
	


	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getArguments_of_script() {
		return arguments_of_script;
	}

	public void setArguments_of_script(String arguments_of_script) {
		this.arguments_of_script = arguments_of_script;
	}
	String script; 
	String state;
	String application_name; 
	

	public ArrayList<RT_Job_Info> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(ArrayList<RT_Job_Info> predecessors) {
		this.predecessors = predecessors;
	}

	public ArrayList<RT_Job_Info> getSuccessors() {
		return successors;
	}

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public void setSuccessors(ArrayList<RT_Job_Info> successors) {
		this.successors = successors;
	}
	
	public String getApplication_name() {
		return application_name;
	}

	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}

	public RT_Job_Info() {
		
	}
	
	public RT_Job_Info(long job_id, String job_name, String user_id, String script, String state) {
		this.job_id=job_id;
		this.job_name=job_name; 
		this.user_id=user_id;
		this.script=script; 
		this.state=state; 
	}
	
	public long getJob_id() {
		return job_id;
	}
	public void setJob_id(long job_id) {
		this.job_id = job_id;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	} 
	
	
	

}
