package com.billydev.orange.service.trigger;


import java.util.ArrayList;
import java.util.List;

import com.billydev.blib.model.DT_Appl_Info;
import com.billydev.blib.model.Event_Info;
import com.billydev.blib.model.RT_Appl_Info;


public interface TriggerService {
	

	
	Boolean createDesignTimeAppl(DT_Appl_Info dtApplInfo);
	
	ArrayList<DT_Appl_Info> listAllDesignTimeAppls();
	


	Boolean updateDesignTimeAppl(DT_Appl_Info dtApplInfo);

	Boolean deleteDesignTimeAppl(long appl_id);

	RT_Appl_Info trigger_application(Event_Info event_info);  
	

	
}
