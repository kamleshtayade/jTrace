package com.spring.app.service;

import javax.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.app.repository.BomheaderRepository;
import com.spring.app.repository.DomheaderRepository;
import com.spring.app.repository.DomlineRepository;
import com.spring.app.repository.ItemctnRepository;
import com.spring.app.repository.ItemmtrRepository;
import com.spring.app.repository.WorkorderheaderRepository;
import com.spring.app.repository.WorkorderlineRepository;

/**
 * Service for managing and retreving entity count.
 * <p/>
 * <p>
 * @author ktayade
 * </p>
 */
@Service
@Transactional
public class CountService {

    private final Logger log = LoggerFactory.getLogger(CountService.class);
    
    @Inject
    private ItemctnRepository itemctnRepository;
    
    @Inject
    private ItemmtrRepository itemmtrRepository;
    
    @Inject
    private WorkorderheaderRepository workorderheaderRepository;
    
    @Inject
    private WorkorderlineRepository workorderlineRepository;
    
    @Inject
    private BomheaderRepository bomheaderRepository;
    
    @Inject
    private DomheaderRepository domheaderRepository;
    
    @Inject
    private DomlineRepository domlineRepository;
    
    private String countJSON;
    
	/**
	 * generateCode
	 * 
	 * used by 
	 * dashboard and reports
	 * 
	 * repository need to inject to get the latest record count
	 * 
	 * @return string json object 
	 */
    
    public String getRecordCount(){
    	
    	 JSONObject jsonObj =new JSONObject();
    	
    	 try {
    		 
			jsonObj.put("ctn", itemctnRepository.count());
			jsonObj.put("itemmtr",itemmtrRepository.count());
			jsonObj.put("woh", workorderheaderRepository.count());
			jsonObj.put("wol", workorderlineRepository.count());
			jsonObj.put("bomh", bomheaderRepository.count());
			jsonObj.put("domh", domheaderRepository.count());
			jsonObj.put("doml", domlineRepository.count());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	
    	return jsonObj.toString();
    	
    }

}
