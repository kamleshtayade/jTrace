package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Workorderheader;
import com.spring.app.repository.WorkorderheaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Workorderheader.
 */
@RestController
@RequestMapping("/api")
public class WorkorderheaderResource {

    private final Logger log = LoggerFactory.getLogger(WorkorderheaderResource.class);

    @Inject
    private WorkorderheaderRepository workorderheaderRepository;

    /**
     * POST  /workorderheaders -> Create a new workorderheader.
     */
    @RequestMapping(value = "/workorderheaders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Workorderheader workorderheader) {
        log.debug("REST request to save Workorderheader : {}", workorderheader);
        workorderheaderRepository.save(workorderheader);
    }

    /**
     * GET  /workorderheaders -> get all the workorderheaders.
     */
    @RequestMapping(value = "/workorderheaders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Workorderheader> getAll() {
        log.debug("REST request to get all Workorderheaders");
        return workorderheaderRepository.findAll();
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
