package com.billydev.blib.model;

import java.util.ArrayList;
import java.util.List;

public class DT_Job_Info {
	long job_Id; 
	String job_type; 
	String DT_Job_Name;
	String qualifier ; 
	String DT_Applicatoin_Name; 
	String predecessor_Condition;
	String predecessor_names; 
	String successor_names; 
	String state; 
	String script_userid; 
	String agent_name; 
	String script; 
    String arguments_Of_Script; 

	public String getJob_type() {
		return job_type;
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
	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getArguments_Of_Script() {
		return arguments_Of_Script;
	}
	public void setArguments_Of_Script(String arguments_Of_Script) {
		this.arguments_Of_Script = arguments_Of_Script;
	}


	
	public long getJob_Id() {
		return job_Id;
	}
	public void setJob_Id(long job_Id) {
		this.job_Id = job_Id;
	}
	public String getDT_Job_Name() {
		return DT_Job_Name;
	}
	public void setDT_Job_Name(String dT_Job_Name) {
		DT_Job_Name = dT_Job_Name;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	public String getDT_Applicatoin_Name() {
		return DT_Applicatoin_Name;
	}
	public void setDT_Applicatoin_Name(String dT_Applicatoin_Name) {
		DT_Applicatoin_Name = dT_Applicatoin_Name;
	}

	public String getPredecessor_Condition() {
		return predecessor_Condition;
	}
	public void setPredecessor_Condition(String predecessor_Condition) {
		this.predecessor_Condition = predecessor_Condition;
	}

	public String getScript_userid() {
		return script_userid;
	}
	public void setScript_userid(String script_userid) {
		this.script_userid = script_userid;
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
	String arguments_of_script;
		
	
	

}
