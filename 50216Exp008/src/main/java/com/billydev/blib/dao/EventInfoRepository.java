package com.billydev.blib.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.billydev.blib.model.DT_Appl_Info;
import com.billydev.blib.model.DT_Job_Info;
import com.billydev.blib.model.Event_Info;

@Repository
public class EventInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    


    
    public DT_Appl_Info getDesignTime_Appl_info(String dt_Application_Name) {

    	DT_Appl_Info  dtAppInfo = new DT_Appl_Info();
    	Object[] values = new Object[] { dt_Application_Name };
    	DT_Job_Info dt_job_info=null; 
    	
    	String sql=    		"select job.DT_Application_Name , job.script,appl.applscript,   "
        		+ " job.agent_name, job.DT_Job_Name, job.script, job.job_id, job.predecessor_condition, "
        		+ "job.predecessor_names, successor_names, job.Arguments_Of_Script, appl.appl_id, appl.appl_create_user_id "
        		+ " from dt_appl_info appl, dt_job_info job where appl.DT_Application_Name=job.DT_Application_Name "
        		+ "and job.DT_application_name=? " ; 
//    	sql=    		"select job.DT_Application_Name ,  job.script "
//        		+ " from dt_appl_info appl, dt_job_info job where appl.DT_Application_Name=job.DT_Application_Name "
//        		+ "and job.DT_application_name=? " ; 
    	/*
    	 * first query the appl info
    	 */
    	ArrayList<DT_Job_Info> arrayListDTJobs= new ArrayList<>(); 
        jdbcTemplate.query
        	  (
        			 /*
        			  * ignore the where filter and ignore the jobs first 
        			  */
                 sql,
        		values,
                new RowCallbackHandler(){  
                    @Override  
                    public void processRow(ResultSet rs) throws SQLException { 
                    	DT_Job_Info dtJobInfo= new DT_Job_Info(); 
                    	dtAppInfo.setAppName(rs.getString("DT_Application_Name"));

                    	dtAppInfo.setApplscript(rs.getString("applscript"));
                    	
                    	dtAppInfo.setAppl_id(rs.getLong("appl_id"));
                    	
                    	dtAppInfo.setAppCreateUserId(rs.getString("appl_create_user_id"));

                    	dtJobInfo.setAgent_name(rs.getString("Agent_Name"));
                    	
                    	
                    	
                    	dtJobInfo.setArguments_of_script(rs.getString("Arguments_Of_Script"));

                    	dtJobInfo.setDT_Applicatoin_Name(rs.getString("DT_Application_Name"));
                    	dtJobInfo.setDT_Job_Name(rs.getString("DT_Job_Name"));

                    	dtJobInfo.setJob_Id(rs.getLong("Job_Id"));
                    	//dtJobInfo.setPredecessor_Condition(rs.getString("Predecessor_Condition"));

                    	dtJobInfo.setPredecessor_names(rs.getString("Predecessor_Names"));
                    	dtJobInfo.setSuccessor_names(rs.getString("Successor_Names"));
                    	/*
                    	 * try to deal with +embedded objects 
                    	 * seems we do not need real job list for design time, only job_id ( job_name) is fine 
                    	 */
//                    	  ArrayList<DT_Job_Info> predecessors= new ArrayList<>();
//                    	  for(String predecessor: Arrays.asList(rs.getString("Predecessor_IDs").split(","))) {
//                    		  DT_Job_Info dt_job_info=designTimeJobRepository.getDesignTimeJobInfoByName(predecessor);
//                    		  predecessors.add(dt_job_info);
//                    	  }
//                    	dtJobInfo.setPredecessors(predecessors);
                    	   
                    	
                    	//dtJobInfo.setQualifier(rs.getString("Qualifier"));
                    	dtJobInfo.setScript(rs.getString("Script"));
                    	//dtJobInfo.setScript_userid(rs.getString("Script_userid"));
                          	/*
                    	 * try to deal with embedded objects 
                    	 */                    	
//                    	  ArrayList<DT_Job_Info> successors= new ArrayList<>(); 
//                    	  for(String successor: Arrays.asList(rs.getString("Successor_IDs").split(","))) {
//                    		  DT_Job_Info dt_job_info=designTimeJobRepository.getDesignTimeJobInfoByName(successor);
//                    		  successors.add(dt_job_info);
//                    	  }
//                    	dtJobInfo.setSuccessors(successors);                    	  
                    	arrayListDTJobs.add(dtJobInfo);
                    }
                }
             );

        dtAppInfo.setJobList(arrayListDTJobs);

        return dtAppInfo;

    }
    
    
    
    
    public Boolean deleteDesignTimeAppl(long appl_id) {
  	  jdbcTemplate.update(
  			  "delete from  dt_appl_info  where appl_id=?", 
  			  appl_id); 
  	  
  	  System.out.println("dtAppInfo: appl_id:"+appl_id);
  	  
  	  return true; 
  }
    
    
    public DT_Appl_Info updateDesignTimeAppl(DT_Appl_Info dtApplInfo) {
    	  jdbcTemplate.update(
    			  "update dt_appl_info set DT_application_name=? ,appl_create_user_id=? where appl_id=?", 
    			  dtApplInfo.getAppName(),
    			  dtApplInfo.getAppCreateUserId(),
    			  dtApplInfo.getAppl_id()); 
    	  
    	  System.out.println("dtAppInfo: appname:"+dtApplInfo.getAppName()
    	           +" createrid:"+dtApplInfo.getAppCreateUserId()+" appl_id:"+dtApplInfo.getAppl_id());
    	  
    	  
    	  
    	  return getDesignTime_Appl_info(dtApplInfo.getAppName()); 
    }
    
    
    public Boolean createDesignTimeAppl(DT_Appl_Info dtApplInfo) {
    	
    	
    	
  	  /*
  	   * get next generation number
  	   */
  	  
    	  Object[] values = new Object[] {dtApplInfo.getAppName(),
    			  Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))),dtApplInfo.getAppCreateUserId() };    	  
  	  
     	
    	
    	/*
    	 * insert into database , need to insert into rt_job as well 
    	 * update the generation number !!
    	 */
        
    	  jdbcTemplate.batchUpdate(
    			  "insert into dt_appl_info(DT_application_name, appl_create_user_id) "
    			  + "values ('"+  dtApplInfo.getAppName()+"','"+dtApplInfo.getAppCreateUserId()+ "')");
   	
    	
    	return true;
    }
    
    
    public ArrayList<Event_Info> getAllEvents(){
    	
    	String sql="select * from event_info";
    	ArrayList<Event_Info> arrayListEvents= new ArrayList<>(); 
    	Object[] values = new Object[] {};
    	System.out.println("query is: "+sql);
    	
        jdbcTemplate.query(
        		sql, 
        		values,
        	    new RowCallbackHandler(){  
                    @Override  
                    public void processRow(ResultSet rs) throws SQLException { 
                    	Event_Info event_info =  new Event_Info();
                    	event_info.setEvent_name(rs.getString("event_name"));
                    	event_info.setDtappname(rs.getString("DT_Application_Name"));
                    	event_info.setNext_scheduled_time(rs.getTimestamp("Next_scheduled_time"));

                    	arrayListEvents.add(event_info);
                    }
        		});
    	
    	return arrayListEvents; 
    }

 


}
