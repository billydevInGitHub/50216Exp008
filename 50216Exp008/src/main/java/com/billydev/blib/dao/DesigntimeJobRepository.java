package com.billydev.blib.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DesigntimeJobRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
    public DT_Job_Info getDesignTimeJobInfoByName(String jobName) {
    	
    	
    	DT_Job_Info dtJobInfo = new DT_Job_Info();
    	Object[] values = new Object[] { jobName };

    	
    	
        jdbcTemplate.query
  	  (
  			 /*
  			  * ignore the where filter and ignore the jobs first 
  			  */
    /**
  		"select Job_id, job_type, DT_Job_Name, Qualifier, DT_Application_name, Predecessor_IDs, Predecessor_Condition, "
  		+ "Successor_IDs, state, tag_for_wait_states,tag_for_run_states,script_userid, agent_name, script,"
  		+ "arguments_Of_Script   from dt_job_info where DT_job_name=? ",
  		values,
          new RowCallbackHandler(){  
              @Override  
              public void processRow(ResultSet rs) throws SQLException { 
              	DT_Job_Info dtJobInfo = new DT_Job_Info();
              	dtJobInfo.setJob_Id(rs.getLong("Job_Id"));
            	dtJobInfo.setJob_type(job_type);

              	arrayListRect.add(rectInfo);
              }
          }
       );
    	
    	

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        jdbcTemplate.query(
                "SELECT job_id, job_name, userid, script, state from rt_job_info",
                (rs, rowNum) -> new RT_Job_Info(rs.getLong("job_id"),
                        rs.getString("job_name"), rs.getString("userid"), rs.getString("script"),rs.getString("state"))
        );

        return result;

    }

        	**/

}
