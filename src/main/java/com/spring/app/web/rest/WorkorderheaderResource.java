package com.spring.app.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Bomline;
import com.spring.app.domain.Workorderheader;
import com.spring.app.domain.Workorderline;
import com.spring.app.repository.BomheaderRepository;
import com.spring.app.repository.BomlineRepository;
import com.spring.app.repository.WorkorderheaderRepository;
import com.spring.app.repository.WorkorderlineRepository;
import com.spring.app.web.rest.util.CodeUtil;
import com.spring.app.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Workorderheader.
 */
@RestController
@RequestMapping("/api")
public class WorkorderheaderResource {

    private final Logger log = LoggerFactory.getLogger(WorkorderheaderResource.class);

    @Inject
    private WorkorderheaderRepository workorderheaderRepository;
    @Inject
    private WorkorderlineRepository workorderlineRepository;
    @Inject
    private BomheaderRepository bomheaderRepository;
    @Inject
    private BomlineRepository bomlineRepository;

    /**
     * POST  /workorderheaders -> Create a new workorderheader.
     */
    @RequestMapping(value = "/workorderheaders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Workorderheader workorderheader) throws URISyntaxException {
        log.debug("REST request to save Workorderheader : {}", workorderheader);
        if (workorderheader.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new workorderheader cannot already have an ID").build();
        }
        workorderheaderRepository.save(workorderheader);
        
        /**
         * Start - Add WOline entries         * 
         * POST /workorderlines --> Create new workorderlines base on BOM structure .
         */        
        Bomline bomline = null;
        Workorderline woline = new Workorderline ();
        List<Bomline> bomlines = bomlineRepository.findByBomheader(bomheaderRepository.findByItemmtr(workorderheader.getItemmtr()));
        
        if(!bomlines.isEmpty()){
        	for(Object childBom : bomlines){
        		bomline = (Bomline) childBom;
        		woline.setItemmtr(bomline.getItemmtr());
        		woline.setBomQty(bomline.getQuantity());
        		woline.setRequQty(bomline.getQuantity() * workorderheader.getQty());
        		woline.setWorkorderheader(workorderheader);
        		workorderlineRepository.save(woline);
        		woline = null;
        	}
        }
        /**
         *  End - Add Woline entries
         */
        log.debug("WOSerial"+CodeUtil.generateCode("WOSerial", workorderheader.getId(), 10));
        return ResponseEntity.created(new URI("/api/workorderheaders/" + workorderheader.getId())).build();
    }

    /**
     * PUT  /workorderheaders -> Updates an existing workorderheader.
     */
    @RequestMapping(value = "/workorderheaders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Workorderheader workorderheader) throws URISyntaxException {
        log.debug("REST request to update Workorderheader : {}", workorderheader);
        if (workorderheader.getId() == null) {
            return create(workorderheader);
        }
        workorderheaderRepository.save(workorderheader);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /workorderheaders -> get all the workorderheaders.
     */
    @RequestMapping(value = "/workorderheaders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Workorderheader>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Workorderheader> page = workorderheaderRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workorderheaders", offset, limit);
        return new ResponseEntity<List<Workorderheader>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workorderheaders/:id -> get the "id" workorderheader.
     */
    @RequestMapping(value = "/workorderheaders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Workorderheader> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Workorderheader : {}", id);
        Workorderheader workorderheader = workorderheaderRepository.findOne(id);
        if (workorderheader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workorderheader, HttpStatus.OK);
    }

    /**
     * DELETE  /workorderheaders/:id -> delete the "id" workorderheader.
     */
    @RequestMapping(value = "/workorderheaders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Workorderheader : {}", id);
        workorderheaderRepository.delete(id);
    }
}
