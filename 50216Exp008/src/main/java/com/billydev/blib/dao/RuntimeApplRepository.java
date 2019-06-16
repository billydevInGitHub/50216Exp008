package com.billydev.blib.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.billydev.blib.model.Diagram_Line_Info;
import com.billydev.blib.model.Diagram_Rect_Info;
import com.billydev.blib.model.RT_Job_Info;
import com.billydev.blib.model.Runtime_Appl_Info;

@Repository
public class RuntimeApplRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired 
    private RuntimeJobRepository jobRepository; 
    
    
    

    public Runtime_Appl_Info get_Runtime_Appl_info(long  appl_id) {
    	/*
    	 * this is for D3 page display,
    	 * todo: to change the above method name to make it related to the D3 stuff 
    	 */

    	Runtime_Appl_Info runtimeAppInfo = new Runtime_Appl_Info();
    	Object[] values = new Object[] {appl_id };
    	
    	ArrayList<Diagram_Rect_Info> arrayListRect= new ArrayList<>(); 
    	ArrayList<Diagram_Line_Info> arrayListLine= new ArrayList<>();  
    	/*
    	 * first query for the rectangles
    	 */
        jdbcTemplate.query
        	  (
        			 /*
        			  * ignore the where filter and ignore the jobs first 
        			  * todo: here we hardcoded appl.generation_number, in future client
        			  * need to pass in the generation number
        			  */
        		"select job.Job_Id, x, y, width, height, x_job_name, y_job_name, job.job_name, job.state,appl.Application_Name   "
        		+ "from appl_diagram_rect rect, RT_appl_info appl, RT_job_info job " + 
        		"where appl.DT_Application_Name=rect.DT_Application_Name " + 
        		"      and job.job_name=rect.job_name and appl.generation_number=job.appl_generation_number  " + 
        		"      and appl.Application_name=job.application_name  and appl.RT_appl_id=? ",
        		values,
                new RowCallbackHandler(){  
                    @Override  
                    public void processRow(ResultSet rs) throws SQLException { 
                    	Diagram_Rect_Info rectInfo = new Diagram_Rect_Info(); 
                    	rectInfo.setX(rs.getInt("x"));
                    	rectInfo.setY(rs.getInt("y"));
                    	rectInfo.setWidth(rs.getInt("width"));
                    	rectInfo.setHeight(rs.getInt("Height"));
                    	rectInfo.setX_job_name(rs.getInt("x_job_name"));
                    	rectInfo.setY_job_name(rs.getInt("y_job_name"));
                    	rectInfo.setJob_name(rs.getString("job_name"));
                    	rectInfo.setState(rs.getString("state"));
                    	arrayListRect.add(rectInfo);
                    	runtimeAppInfo.setApplicationName(rs.getString("Application_Name"));
                    }
                }
             );

        
        /*
         * then query for the lines 
         */
        

        
        jdbcTemplate.query
  	  (
  			 /*
  			  * ignore the where filter and ignore the jobs first 
  			  * todo : here we hardcode the rt_appl_info generation number 
  			  */
  		"select  x1, y1, x2, y2, hasArrow, appl.application_name "
  		+ "from RT_appl_info appl, appl_diagram_line line "
  		+ "where line.DT_Application_Name=appl.DT_application_name and  appl.RT_appl_id=?  ",
  		values,
          new RowCallbackHandler(){  
              @Override  
              public void processRow(ResultSet rs) throws SQLException { 
              	Diagram_Line_Info lineInfo = new Diagram_Line_Info();
              	lineInfo.setX1(rs.getInt("x1"));
              	lineInfo.setY1(rs.getInt("y1"));
              	lineInfo.setX2(rs.getInt("x2"));
              	lineInfo.setY2(rs.getInt("y2"));
              	lineInfo.setHasArrow(rs.getString("hasArrow"));
              	arrayListLine.add(lineInfo);
              }
          }
       );
        runtimeAppInfo.setDatasetForRect(arrayListRect);
        runtimeAppInfo.setDatasetForLine(arrayListLine);
        return runtimeAppInfo;

    }


    public long  insert_Runtime_Appl_info(Runtime_Appl_Info rt_appl_info) {

    	
  	  /*
  	   * get next generation number
  	   */
  	  
    	  Object[] values = new Object[] { rt_appl_info.getApplicationName() };    	  
  	  

    	  /*
    	   * need to set the generation number , generaton number plus rt_application_name is the "key" 
    	   * of a runtime application,  -- auto generated "rt_application id " is not that useful as it 
    	   * includes all the rt_applications
    	   */
    	  
    	 jdbcTemplate.query
    	  (

    		"select count(*) totalAppls from rt_appl_info where application_name=? ",
    		values,
            new RowCallbackHandler(){  
                @Override  
                public void processRow(ResultSet rs) throws SQLException { 
                  System.out.println("RuntimeApplRepository: get total of rt_appl_info: rs.getLong(1): "+rs.getLong(1)
                  		+"    rs.getLong(totalAppls): "+rs.getLong("totalAppls"));
              	  rt_appl_info.setGenerationNumber(rs.getLong("totalAppls")+1);
                }
            }
    	  );
      	
    	
    	/*
    	 * insert into database , need to insert into rt_job as well 
    	 * update the generation number !!
    	 * need to return the appl id 
    	 */
        

    	  
    	  final String sql_insert="insert into rt_appl_info(DT_application_name, application_name, generation_number) "
    			  + "values (?,?,?)" ;
    	  
    	  KeyHolder keyHolder = new GeneratedKeyHolder();
    	  
    	  jdbcTemplate.update(
    	    	    connection -> {
    	                PreparedStatement ps = connection.prepareStatement(sql_insert, new String[] {"RT_appl_id"});
    	                  System.out.println("Sql_insert is:"+sql_insert);
    	                  ps.setString(1, rt_appl_info.getDT_ApplicationName());
    					  ps.setString(2, rt_appl_info.getApplicationName() );
    					  ps.setLong(3, rt_appl_info.getGenerationNumber());

    	                 return ps;
    	            }, keyHolder);
   	  
   	
    	return keyHolder.getKey().longValue(); 
    }


	public Runtime_Appl_Info get_Runtime_Appl_info_By_RT_Job_Id(long job_id) {
    	Runtime_Appl_Info runtimeAppInfo = new Runtime_Appl_Info();
    	Object[] values = new Object[] { job_id };    	
    	
    	/*
    	 * get the runtime application from job_id 
    	 */
        jdbcTemplate.query
  	  (
  			 /*
  			  * ignore the where filter and ignore the jobs first 
  			  * todo: here we hardcoded appl.generation_number, in future client
  			  * need to pass in the generation number
  			  */
  		"select appl.application_name, appl.generation_number, appl.rt_appl_id, "
  		+ "job.job_id, job.job_name, job.application_name, job.appl_generation_number,  "
  		+ " job.predecessor_names, job.state, job.agent_name, job.script, "
  		+ " job.arguments_of_script, job.userid   "
  		+ "from  RT_appl_info appl, RT_job_info job " + 
  		" where  job.appl_generation_number=appl.generation_number " + 
  		"      and appl.Application_name=job.application_name  and job.job_id=? ",
  		values,
          new RowCallbackHandler(){  
              @Override  
              public void processRow(ResultSet rs) throws SQLException { 
              	runtimeAppInfo.setApplicationName(rs.getString("appl.application_name"));
              	runtimeAppInfo.setGenerationNumber(rs.getLong("appl.generation_number"));
              }
          }
       );
    	
        
        /*
         * use this runtime application name to query all jobs 
         */
    	
        values = new Object[] { runtimeAppInfo.getApplicationName(), runtimeAppInfo.getGenerationNumber() };  
    	

    	ArrayList<RT_Job_Info>   arrayList_RT_job_info = new ArrayList<>();  	
    	


    	/*
    	 * first query for the rectangles
    	 */
        jdbcTemplate.query
        	  (
        			 /*
        			  * ignore the where filter and ignore the jobs first 
        			  * todo: here we hardcoded appl.generation_number, in future client
        			  * need to pass in the generation number
        			  */
        		"select appl.application_name, appl.generation_number, appl.rt_appl_id, "
        		+ "job.job_id, job.job_name, job.application_name, job.appl_generation_number,  "
        		+ " job.predecessor_names, job.state, job.agent_name, job.script, "
        		+ " job.arguments_of_script, job.userid   "
        		+ "from  RT_appl_info appl, RT_job_info job " + 
        		" where  job.appl_generation_number=appl.generation_number " + 
        		"      and appl.Application_name=job.application_name  and job.application_name=? "
        		+ " and job.appl_generation_number=? ",
        		values,
                new RowCallbackHandler(){  
                    @Override  
                    public void processRow(ResultSet rs) throws SQLException { 
                    	RT_Job_Info  rtJobInfo = new RT_Job_Info(); 
                    	rtJobInfo.setJob_id(rs.getLong("job.job_id"));
                    	rtJobInfo.setJob_name(rs.getString("job.job_name"));
                       	rtJobInfo.setApplication_name(rs.getString("job.application_name"));
                       	rtJobInfo.setAppl_generation_number(rs.getLong("job.appl_generation_number"));
                       	rtJobInfo.setPredecessor_names(rs.getString("job.predecessor_names"));
                       	rtJobInfo.setState(rs.getString("job.state"));
                       	rtJobInfo.setAgent_name(rs.getString("job.agent_name"));
                       	rtJobInfo.setScript(rs.getString("job.script"));
                       	rtJobInfo.setArguments_of_script(rs.getString("job.arguments_of_script"));
                       	rtJobInfo.setUser_id(rs.getString("job.userid"));
                    	arrayList_RT_job_info.add(rtJobInfo);
                    }
                }
             );
           runtimeAppInfo.setJobs(arrayList_RT_job_info);
        


        return runtimeAppInfo;
	}

}
