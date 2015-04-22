package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Workorderline;
import com.spring.app.repository.WorkorderlineRepository;
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
 * REST controller for managing Workorderline.
 */
@RestController
@RequestMapping("/api")
public class WorkorderlineResource {

    private final Logger log = LoggerFactory.getLogger(WorkorderlineResource.class);

    @Inject
    private WorkorderlineRepository workorderlineRepository;

    /**
     * POST  /workorderlines -> Create a new workorderline.
     */
    @RequestMapping(value = "/workorderlines",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Workorderline workorderline) {
        log.debug("REST request to save Workorderline : {}", workorderline);
        workorderlineRepository.save(workorderline);
    }

    /**
     * GET  /workorderlines -> get all the workorderlines.
     */
    @RequestMapping(value = "/workorderlines",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Workorderline> getAll() {
        log.debug("REST request to get all Workorderlines");
        return workorderlineRepository.findAll();
    }

    /**
     * GET  /workorderlines/:id -> get the "id" workorderline.
     */
    @RequestMapping(value = "/workorderlines/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Workorderline> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Workorderline : {}", id);
        Workorderline workorderline = workorderlineRepository.findOne(id);
        if (workorderline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workorderline, HttpStatus.OK);
    }

    /**
     * DELETE  /workorderlines/:id -> delete the "id" workorderline.
     */
    @RequestMapping(value = "/workorderlines/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Workorderline : {}", id);
        workorderlineRepository.delete(id);
    }
}
