/**
 * 
 */
package com.spring.app.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Authority;
import com.spring.app.domain.User;
import com.spring.app.service.CountService;
import com.spring.app.web.rest.dto.UserDTO;

/**
 * @author ktayade
 *
 */
/**
 * REST controller for entity record count.
 */
@RestController
@RequestMapping("/api")
public class CountResource {
	
    private final Logger log = LoggerFactory.getLogger(CountResource.class);
    
    @Inject
    private CountService countService;
    

    /**
     * GET  /rcount -> get the record Count.
     */
    @RequestMapping(value = "/count",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> getCount() {
        String count = countService.getRecordCount();
        log.debug("count:"+count);
        return new ResponseEntity<>(count,HttpStatus.OK);
    }


}
