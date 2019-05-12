package com.billydev.blib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.billydev.blib.CommonConfiguration;
import com.billydev.blib.model.RT_Job_Info;

@Repository
public class RuntimeJobRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Boolean insert_new_rt_job(RT_Job_Info rt_job_info) {
    	
    	
    	final String INSERT_SQL = "insert into rt_job_info (job_type, job_name,qualifier, application_name, appl_generation_number, "
  			  + "Predecessor_names, Successor_names, state, Agent_name,script, arguments_Of_script, UserID ) "
  			  + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	jdbcTemplate.update(
    	    connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"job_id"});
                
                  ps.setString(1, rt_job_info.getJob_type());
				  ps.setString(2, rt_job_info.getJob_name() );
				  ps.setString(3, rt_job_info.getQualifier());
				  /*
				   * the following two lines job application and jobs 
				   */
				  ps.setString(4, rt_job_info.getApplication_name());
				  ps.setLong(5, rt_job_info.getAppl_generation_number());
				  ps.setString(6, rt_job_info.getPredecessor_names());
				  ps.setString(7, rt_job_info.getSuccessor_names());
				  ps.setString(8, rt_job_info.getState());
				  ps.setString(9, rt_job_info.getAgent_name());
				  ps.setString(10, rt_job_info.getScript());
				  ps.setString(11, rt_job_info.getArguments_of_script());
				  ps.setString(12, rt_job_info.getUser_id());
                 return ps;
            }, keyHolder);
    	
    	rt_job_info.setJob_id(keyHolder.getKey().longValue()); 
    	System.out.println("RuntimeJobRepository: inserted rt job:[ "
    			+ " id:" +rt_job_info.getJob_id()
    			+" job_name:"	+rt_job_info.getJob_name()
    			+" application name:"+rt_job_info.getApplication_name()
    			+" appl generation number: "+rt_job_info.getAppl_generation_number()
    			+"]");
    	return true; 
    }
    

    public List<RT_Job_Info> findAll() {

        List<RT_Job_Info> result = jdbcTemplate.query(
                "SELECT job_id, job_name, userid, script, state from rt_job_info",
                (rs, rowNum) -> new RT_Job_Info(rs.getLong("job_id"),
                        rs.getString("job_name"), rs.getString("userid"), rs.getString("script"),rs.getString("state"))
        );

        return result;

    }
    
    public boolean  setRTJobRunning(RT_Job_Info rt_job_info) {
    	
  	  jdbcTemplate.update(
			  "update rt_job_info set state=? where job_Id=?", 
			  CommonConfiguration.JOB_STATE_RUNNING, 
			  rt_job_info.getJob_id()); 

    	
    	return true; 
    }
    
    
    public boolean setRTJobComplete(RT_Job_Info rt_job_info) {
    	
  	  jdbcTemplate.update(
			  "update rt_job_info set state=? where job_Id=?", 
			  CommonConfiguration.JOB_STATE_COMPLETED, 
			  rt_job_info.getJob_id()); 

    	
    	return true; 
    }
    
    public  RT_Job_Info getRTJobInfo(long jobId) {
    	
    	RT_Job_Info rtJobInfo = new RT_Job_Info();
    	Object[] values = new Object[] { jobId };
    	

    	
    	
        jdbcTemplate.query
  	  (
  			 /*
  			  * ignore the where filter and ignore the jobs first 
  			  */
  		"select * from rt_job_info where Job_id=?",
  		values,
          new RowCallbackHandler(){  
              @Override  
              public void processRow(ResultSet rs) throws SQLException { 
              	rtJobInfo.setJob_id(rs.getLong("Job_Id"));
              	rtJobInfo.setJob_type(rs.getString("Job_Type"));
              	rtJobInfo.setJob_name(rs.getString("Job_Name"));
              	rtJobInfo.setQualifier(rs.getString("Qualifier"));
              	rtJobInfo.setApplication_name(rs.getString("Application_Name"));
              	rtJobInfo.setAppl_generation_number(rs.getLong("appl_generation_number"));
              	rtJobInfo.setPredecessor_names(rs.getString("Predecessor_Names"));
              	rtJobInfo.setState(rs.getString("state"));
              	rtJobInfo.setAgent_name(rs.getString("Agent_Name"));
              	rtJobInfo.setScript(rs.getString("script")); 
              	rtJobInfo.setArguments_of_script(rs.getString("arguments_Of_Script"));
              	rtJobInfo.setUser_id(rs.getString("UserID"));
              }
          }
       );
    	
    	return rtJobInfo; 
    }
 

    

}
