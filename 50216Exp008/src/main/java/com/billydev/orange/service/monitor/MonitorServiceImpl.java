package com.billydev.orange.service.monitor;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billydev.blib.dao.RuntimeApplRepository;
import com.billydev.blib.dao.RuntimeJobRepository;
import com.billydev.blib.external.CommonConfiguration;
import com.billydev.blib.external.CommonMsgInQueue;
import com.billydev.blib.jobengine.RuntimeApplicationProcessor;
import com.billydev.blib.model.RT_Job_Info;
import com.billydev.blib.model.Runtime_Appl_Info;


@Service("monitorService")
public class MonitorServiceImpl implements MonitorService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	@Autowired 
	private RuntimeApplicationProcessor runtimeApplicationProcessor; 
	
	static{
		//users= populateDummyUsers();
	}

//    @Autowired
//    DataSource dataSource;

    @Autowired
    private RuntimeJobRepository rtJobRepository;

    @Autowired
    private RuntimeApplRepository rtApplRepository;


    
//	public Runtime_Appl_Info get_Runtime_Appl_info_By_Rt_Job_Info(RT_Job_Info rtJobInfo) {
//
//		/*
//		 * todo: this service is kind of temp one
//		 */
//		Runtime_Appl_Info rtai = rtApplRepository.get_Runtime_Appl_info_By_RT_Job_Id(rtJobInfo.getJob_id()); 
//
//		return rtai;
//	}


	public Runtime_Appl_Info get_Runtime_Appl_info(long appl_id) {

		/*
		 * todo: this service is kind of temp one
		 */
		Runtime_Appl_Info rtai = rtApplRepository.get_Runtime_Appl_info(appl_id); 

		return rtai;
	}
	
	public List<RT_Job_Info> findAllJobs() {

		
		List<RT_Job_Info> list = rtJobRepository.findAll();
//		List<User> users = new ArrayList<User>();
//		for ( Customer c: list){
//			User u = new User(c.getId(),c.getName(),40,100000);
//			users.add(u);
//			System.out.println(u.getId()+":"+u.getName());
//		}

		return list;
	}


	@Override
	@Transactional 
	public Boolean updateFromClient(CommonMsgInQueue msgInQueue) {
		/*
		 * 1 Got the message from client
		 * 2 update database using @Transactional implementation 
		 * 3 Do the overall check of the application state change
		 * 4 Send out the new message to engine
		 */
		RT_Job_Info rt_job_info=null; 
		
		switch(msgInQueue.getMsgType()) {
		case CommonConfiguration.MSG_JOB_CANCELLED:
			break; 
		case CommonConfiguration.MSG_JOB_COMPLETED:
			/*
			 * update rt_job_info then do the overall check
			 */
			rt_job_info=rtJobRepository.getRTJobInfo(msgInQueue.getJobId()); 
			rt_job_info.setState(CommonConfiguration.JOB_STATE_COMPLETED);
			rtJobRepository.setRTJobComplete(rt_job_info); 
			
		}
		
		/*
		 * after state update, we need to do the overall state transform of the application
		 */
		Runtime_Appl_Info rtApplInfo=rtApplRepository.get_Runtime_Appl_info_By_RT_Job_Id(rt_job_info.getJob_id());
		
		/*
		 * we got the predecessor names setup, but predecessor list not setup
		 * 
		 */
		
		RuntimeApplicationProcessor.setupPrdecessorSuccessorListByName(rtApplInfo.getJobs());
		
		
		return runtimeApplicationProcessor.overallCheckAndProcess(rtApplInfo); 

	}
	
	
	/*
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public User findByName(String name) {
		for(User user : users){
			if(user.getName().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(User user) {
		return findByName(user.getName())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	private static List<User> populateDummyUsers(){
		List<User> users = new ArrayList<User>();
		Address address1 =new Address("123","Calgary","Alberta","Canada");
		Address address2 = new Address("678", "Vancouver", "BC", "Canada");
		User user1=new User(counter.incrementAndGet(),"Sam",30, 70000);
			user1.setAddress(address1);
			String[] jobTitles={"Programmer", "Manager"};
			user1.setJobTitles(jobTitles);
		User user2=new User(counter.incrementAndGet(),"Tom",40, 50000);
			jobTitles=new String[]{"Programmer", "Accountant"};
		    user2.setAddress(address1);
		    user2.setJobTitles(jobTitles);
		User user3=new User(counter.incrementAndGet(),"Jerome",45, 30000);
			jobTitles=new String[]{"VP", "Accountant"};
		     user3.setAddress(address2);
		     user3.setJobTitles(jobTitles);
		User user4=new User(counter.incrementAndGet(),"Silvia",50, 40000);
			 jobTitles=new String[]{"VP", "HR"};
		     user4.setAddress(address2);
		     user4.setJobTitles(jobTitles);
		     
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		return users;
	}
 */
}
