package com.billydev.blib.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.billydev.blib.external.CommonMsgInQueue;
import com.billydev.blib.external.CommonUtils;
import com.billydev.blib.model.Event_Info;
import com.billydev.blib.model.DT_Appl_Info;
import com.billydev.blib.model.RT_Appl_Info;
import com.billydev.blib.model.Runtime_Appl_Info;
import com.billydev.blib.model.WrapOfListDTApplInfo;
import com.billydev.orange.service.monitor.MonitorService;
import com.billydev.orange.service.trigger.TriggerService;

//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin(origins = { "http://localhost:8080","*"},maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);



	@Autowired
	TriggerService triggerService;	
	
	@Autowired
	MonitorService monitorService;
	
	
	/**
	 * trigger service 
	 */
	
	/*
	 * trigger the application through event
	 * todo: url is hardcoded without event info passed-in, need to use event as input  
	 */
//	@RequestMapping(value="/trigger_appl/", method=RequestMethod.GET , produces = "application/json") 
//	public ResponseEntity<RT_Appl_Info> trigger_application(){		
//		
//		/*
//		 * todo: just hardcode the event here, the dt_application_name supposed be passed from url
//		 */
//		
//		Event_Info event_info = new Event_Info(); 
//		event_info.setDtappname("data_88010_U001_Diagram_c");
//		RT_Appl_Info rtApplInfo=triggerService.trigger_application(event_info); 
//		
//		ResponseEntity<RT_Appl_Info> response= new ResponseEntity<>(rtApplInfo, HttpStatus.OK);
//		return response; 
//	}
	
	/*
	 * trigger the application through event
	 * todo: url is hardcoded without event info passed-in, need to use event as input  
	 */
	@RequestMapping(value="/rtapplication/", method=RequestMethod.POST , produces = "application/json") 
	public ResponseEntity<RT_Appl_Info> createRTapplication(@RequestBody Event_Info event_info){		
		
		/*
		 * todo: just hardcode the event here, the dt_application_name supposed be passed from url
		 */
		
		//Event_Info event_info = new Event_Info(); 
		//event_info.setDT_Application_Name("data_88010_U001_Diagram_c");
		RT_Appl_Info rtApplInfo=triggerService.trigger_application(event_info); 
		
		ResponseEntity<RT_Appl_Info> response= new ResponseEntity<>(rtApplInfo, HttpStatus.OK);
		return response; 
	}
	
	
	/*
	 * job cancelled msg received 
	 */
	@RequestMapping(value="/status_update/", method=RequestMethod.POST, produces = "application/json") 
	public ResponseEntity<Boolean> status_update(@RequestBody CommonMsgInQueue message){
		/*
		 * todo: now just simply printout the input JSON object
		 */
		System.out.println("RestApiController:  WebSite received the request! msg in queue:"
				+CommonUtils.getMsgInfo(message));

		
		boolean  result=monitorService.updateFromClient(message);
		
		ResponseEntity<Boolean> response= new ResponseEntity<>(new Boolean(result), HttpStatus.OK);
		return response;
	}	
	
	
	/**
	 * Runtime application info
	 */
	@RequestMapping(value = "/rtapplication/{appl_id}", method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<Runtime_Appl_Info> get_runtime_appl_info(@PathVariable("appl_id") long appl_id) {
		Runtime_Appl_Info  rtai= monitorService.get_Runtime_Appl_info(appl_id);
		/*
		 * temp remove this check
		 */
//		if (rtai.isEmpty()) {
//			return new ResponseEntity(HttpStatus.NO_CONTENT);
//			// You many decide to return HttpStatus.NOT_FOUND
//		}
		System.out.println("Returning Runtime_Appl_MonitorView is:"+rtai); 
		
		ResponseEntity<Runtime_Appl_Info> response=new ResponseEntity<Runtime_Appl_Info>(rtai, HttpStatus.OK);
		
		System.out.println("Response entity is:"+response);
		
		return response ;
	}	
	
	
	
	/**
	 * 
	 * Design Time Application CRUD
	 * @param message
	 * @return
	 */

	@RequestMapping(value = "/dtapplication/", method = RequestMethod.POST)
	public ResponseEntity<?> createDTAppl(@RequestBody DT_Appl_Info dtApplInfo) {
		logger.info("Creating New DT application{}", dtApplInfo);


		Boolean returnObject=triggerService.createDesignTimeAppl(dtApplInfo);

		return new ResponseEntity<Boolean>(returnObject, HttpStatus.CREATED);
	}
	
	//todo:remove "/createDTAppl/"
	@RequestMapping(value = "/createDTAppl/", method = RequestMethod.POST)
	public ResponseEntity<?> createDTApplOld(@RequestBody DT_Appl_Info dtApplInfo) {
		logger.info("Creating New DT application{}", dtApplInfo);


		Boolean returnObject=triggerService.createDesignTimeAppl(dtApplInfo);

		return new ResponseEntity<Boolean>(returnObject, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/dtapplication/{appl_id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDTAppl(@PathVariable("appl_id") long appl_id) {
		logger.info("delete DT application, appl_id:", appl_id);


		Boolean returnObject=triggerService.deleteDesignTimeAppl(appl_id);

		return new ResponseEntity<Boolean>(returnObject, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/deleteDTAppl/", method = RequestMethod.POST)
	public ResponseEntity<?> deleteDTAppl(@RequestBody DT_Appl_Info dtApplInfo) {
		logger.info("delete DT application, appl_id:", dtApplInfo.getAppl_id());


		Boolean returnObject=triggerService.deleteDesignTimeAppl(dtApplInfo.getAppl_id());

		return new ResponseEntity<Boolean>(returnObject, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/dtapplication/", method = RequestMethod.PATCH)
	public ResponseEntity<?> updateDTAppl(@RequestBody DT_Appl_Info dtApplInfo) {
		logger.info("Updating DT application{}", dtApplInfo);


		DT_Appl_Info returnObject=triggerService.updateDesignTimeAppl(dtApplInfo);

		return new ResponseEntity<DT_Appl_Info>(returnObject, HttpStatus.OK);
	}
	

	
	
	@RequestMapping(value="/getListOfDTAppls/", method=RequestMethod.GET , produces = "application/json") 
	public ResponseEntity<WrapOfListDTApplInfo> listOfDTAppls(){	
		ArrayList<DT_Appl_Info> listOfAppInfo= triggerService.listAllDesignTimeAppls();
		WrapOfListDTApplInfo returnObject= new WrapOfListDTApplInfo();
		returnObject.setDtApplList(listOfAppInfo);
		
		if (listOfAppInfo.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<WrapOfListDTApplInfo>(returnObject, HttpStatus.OK);
	
	}

	@RequestMapping(value="/events/", method=RequestMethod.GET , produces = "application/json") 
	public ResponseEntity<ArrayList<Event_Info>> listEvents(){	
		ArrayList<Event_Info> listOfEvents= triggerService.getAllEvents();
//		WrapOfListDTApplInfo returnObject= new WrapOfListDTApplInfo();
//		returnObject.setDtApplList(listOfAppInfo);
		
		if (listOfEvents.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You may decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<ArrayList<Event_Info>>(listOfEvents, HttpStatus.OK);
	
	}
	
	@RequestMapping(value="/dtapplications/", method=RequestMethod.GET , produces = "application/json") 
	public ResponseEntity<WrapOfListDTApplInfo> listDTapplications(){	
		ArrayList<DT_Appl_Info> listOfAppInfo= triggerService.listAllDesignTimeAppls();
		WrapOfListDTApplInfo returnObject= new WrapOfListDTApplInfo();
		returnObject.setDtApplList(listOfAppInfo);
		
		if (listOfAppInfo.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<WrapOfListDTApplInfo>(returnObject, HttpStatus.OK);
	
	}

}