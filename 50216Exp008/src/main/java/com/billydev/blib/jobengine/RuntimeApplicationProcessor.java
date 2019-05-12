package com.billydev.blib.jobengine;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billydev.blib.CommonConfiguration;
import com.billydev.blib.CommonMsgInQueue;
import com.billydev.blib.CommonUtils;
import com.billydev.blib.Configuration;
import com.billydev.blib.dao.RuntimeJobRepository;
import com.billydev.blib.model.RT_Job_Info;
import com.billydev.blib.model.Runtime_Appl_Info;
import com.fasterxml.jackson.databind.ObjectMapper;




@Service
public class RuntimeApplicationProcessor {
	
	private ArrayList<CommonMsgInQueue> messages = new ArrayList<>(); 
	
    @Autowired
    private RuntimeJobRepository rtJobRepository;
	
	public static Boolean setupPrdecessorSuccessorListByName(ArrayList<RT_Job_Info>  list_RT_Job_Info) {
		
		
		
		HashMap<String, RT_Job_Info> hashMapJobs= new HashMap<>(); 
		for( RT_Job_Info rt_job_info_InList  : list_RT_Job_Info   ) {
			hashMapJobs.put(rt_job_info_InList.getJob_name(),rt_job_info_InList ); 
		}
		

		/*
		 * need to resolve predecessor id and successor id into the real job objects
		 * we can only search within this ArrayList for newly triggered jobs !! 
		 */
		for( RT_Job_Info rt_job_info_ForPredecessorAndSuccessor  : list_RT_Job_Info   ) {			
			
      	  ArrayList<RT_Job_Info> predecessors= new ArrayList<>();
      	  String predecessorIds=rt_job_info_ForPredecessorAndSuccessor.getPredecessor_names()==null?"":
      		  rt_job_info_ForPredecessorAndSuccessor.getPredecessor_names();
      	  
      	     for(String predecessor : predecessorIds.split(",")){
      	    	 if(hashMapJobs.containsKey(predecessor)) {
      	    		 
      	    		predecessors.add(hashMapJobs.get(predecessor)); 
      	    	 }
      	     }

      	   rt_job_info_ForPredecessorAndSuccessor.setPredecessors(predecessors);

       	  ArrayList<RT_Job_Info> successors= new ArrayList<>();
       	  String successorIds= rt_job_info_ForPredecessorAndSuccessor.getSuccessor_names()==null?"":
       		  rt_job_info_ForPredecessorAndSuccessor.getSuccessor_names();
   	      for(String successor :successorIds.split(",")){
  	    	 if(hashMapJobs.containsKey(successor)) {
  	    		 
  	    		successors.add(hashMapJobs.get(successor)); 
  	    	 }
  	     }

   	      rt_job_info_ForPredecessorAndSuccessor.setPredecessors(predecessors);     
   	      rt_job_info_ForPredecessorAndSuccessor.setSuccessors(successors);
			
		}		
		
				
	
		
	return true; 
	}
	
	
	
	private Boolean sendUpdatedJobMsgToEngine(ArrayList<CommonMsgInQueue> messages) {
		
		/*
		 * need to send out the message through http client
		 */
//		HttpClient client = new HttpClient();
//		PostMethod method = new PostMethod(Configuration.ENGINE_HTTP_URL);
		
		
	    ObjectMapper mapper=new ObjectMapper(); 
		
		Socket socket = null;
		PrintWriter out=null;


		String result=""; 
		 try {
			 System.out.println("RuntimeApplicationProcessor : =====about to send msg list to job engine, Msg List is:"
		        		+CommonUtils.getMsgInfoArrayList(messages)+" result is:"+result);	
			 
			 for(CommonMsgInQueue msgInQueue:messages) {
				socket = new Socket(Configuration.ENGINE_ADDRESS, Configuration.ENGINE_PORT);
				
//				 System.out.println("RuntimeApplicationProcessor : sending msg to engine, Msg is:"
//			        		+CommonUtils.getMsgInfo(msgInQueue)+" result is:"+result);	
				out = new PrintWriter(socket.getOutputStream(), true);
				
				
				out.println(mapper.writeValueAsString(msgInQueue));
		        System.out.println("RuntimeApplicationProcessor : msg sent to job engine, Msg is:"
		        		+CommonUtils.getMsgInfo(msgInQueue)+" result is:"+result);						
			 }
		        
		    } catch (Exception e) {
		        System.out.println("result is:"+result);
		        throw new RuntimeException("RuntimeApplicationProcessor: error send result to core engine!"); 
		    } finally {
		        try {
		        	if(socket!=null) {
					socket.close();
		        	}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

		 return true; 
	}
	
	public  Boolean  overallCheckAndProcess(Runtime_Appl_Info runtime_appl_info){

		/*
		 *  messages = new ArrayList<>();
		 */
		 messages = new ArrayList<>();
		
		/*
		 * still in the sync block , no state changes supposed to happen 
		 * check all the jobs of runtime application and decide which job need to 
		 * run , then send out message to job engine; 
		 * sometimes manual intervention message came like FC the job, but the job
		 * is already completed, then the message will be consumed 
		 */
		
		/*
		 * just poll all the runtime jobs: 
		 * 1. For any job check if the predecessor  completed then
		 *      if this job is just a link we can complete it right away, 
		 *          then we need loop from the beginning; 
		 *      if this job is linux job, then start to run it ( send message out)  
		 *      todo: there might be hold and other state need to be added here 
		 * 2. return all the "action" message 
		 *
		 * 
		 */
		
		/*
		 * get all runtime_job_info
		 */
		ArrayList<RT_Job_Info> list_RT_Job_Info= runtime_appl_info.getJobs(); 
		
		Check_All_Jobs:	
				for(RT_Job_Info rt_job_info:list_RT_Job_Info) {
					if(isPredecessorCompleted(rt_job_info)) {
						if(isLinkJob(rt_job_info)) {
							setComplete(rt_job_info);
							break Check_All_Jobs; 
						}else {
							/*
							 * todo : hardcode here if job completed then not trigger 
							 */
							if(rt_job_info.getState()!=null&&(!rt_job_info.getState().equals(Configuration.JOB_STATE_COMPLETE))
									&&(!rt_job_info.getState().equals(Configuration.JOB_STATE_RUNNING))) {
								System.out.println("RuntimeApplicatinProcessor: adding new job to msg sending queue, "
										+ "rt_job_info: job_id:"+rt_job_info.getJob_id()+" job_name:"+rt_job_info.getJob_name()
										+ " job state: "+rt_job_info.getState()); 
								/*
								 * 
								 */
								setRunning(rt_job_info); //so it will not be sent out again
								trigger_job(rt_job_info);//add the message
							}
						}
					}
				}
		
		sendUpdatedJobMsgToEngine(messages);
		
		return true;
	}


	private void trigger_job(RT_Job_Info rt_job_info) {
		
		CommonMsgInQueue msgInQueue= new CommonMsgInQueue();
		
		/*
		 * todo: the following still has some hardcoded stuff
		 */
		msgInQueue.setMsgType(CommonConfiguration.MSG_JOB_ACTION_CREATE_A_NEW_JOB);
		msgInQueue.setJobName(rt_job_info.getJob_name());
		msgInQueue.setTarget(CommonConfiguration.CLIENT_MACHINE_ADDRESS);//todo: we just hard code here in database, we can use agentname field
		msgInQueue.setTargetPort(CommonConfiguration.CLIENT_LISTEN_PORT);//todo: we need to get this from db as well
		msgInQueue.setJobScript(rt_job_info.getScript());
		msgInQueue.setJobId(rt_job_info.getJob_id());
		
		
		messages.add(msgInQueue); 
	}

	
	
	public void setRunning(RT_Job_Info rt_job_info) {
		rt_job_info.setState(Configuration.JOB_STATE_RUNNING);
		/*
		 * also need sync db
		 */
		rtJobRepository.setRTJobRunning(rt_job_info);
		
	}

	public void setComplete(RT_Job_Info rt_job_info) {
		rt_job_info.setState(Configuration.JOB_STATE_COMPLETE);
		/*
		 * also need sync db
		 */
		rtJobRepository.setRTJobComplete(rt_job_info);
		
	}


	private boolean isLinkJob(RT_Job_Info rt_job_info) {
		if(rt_job_info.getJob_type()!=null&&rt_job_info.getJob_type().equals(Configuration.JOB_TYPE_LINK)) {
			return true; 
		}
		return false;
	}


	private boolean isPredecessorCompleted(RT_Job_Info rt_job_info) {

		if(rt_job_info.getPredecessors()==null) {
			System.out.println("RuntimeApplicationProcessor: getPredecessors is null for jobid: "
		         +rt_job_info.getJob_id()+ " jobName:"+rt_job_info.getJob_name()); 
			return true; 
		}
		for(RT_Job_Info predecessorJob : rt_job_info.getPredecessors()) {
			if(predecessorJob.getState().equals(Configuration.JOB_STATE_COMPLETE)){
				return true;
			}else {
				return false;
			}
		}
			
		/*
		 * if no predecessor, the job is supposed to be completed
		 */
		return true;
	}

	/*
	 * there might be another process method here with input message 
	 * it can call the above overallCheckAndProcess method 
	 */
}
