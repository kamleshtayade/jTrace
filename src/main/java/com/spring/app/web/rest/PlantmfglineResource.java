package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Plantmfgline;
import com.spring.app.repository.PlantmfglineRepository;
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
 * REST controller for managing Plantmfgline.
 */
@RestController
@RequestMapping("/api")
public class PlantmfglineResource {

    private final Logger log = LoggerFactory.getLogger(PlantmfglineResource.class);

    @Inject
    private PlantmfglineRepository plantmfglineRepository;

    /**
     * POST  /plantmfglines -> Create a new plantmfgline.
     */
    @RequestMapping(value = "/plantmfglines",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Plantmfgline plantmfgline) {
        log.debug("REST request to save Plantmfgline : {}", plantmfgline);
        plantmfglineRepository.save(plantmfgline);
    }

    /**
     * GET  /plantmfglines -> get all the plantmfglines.
     */
    @RequestMapping(value = "/plantmfglines",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Plantmfgline> getAll() {
        log.debug("REST request to get all Plantmfglines");
        return plantmfglineRepository.findAll();
    }

    /**
     * GET  /plantmfglines/:id -> get the "id" plantmfgline.
     */
    @RequestMapping(value = "/plantmfglines/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plantmfgline> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Plantmfgline : {}", id);
        Plantmfgline plantmfgline = plantmfglineRepository.findOne(id);
        if (plantmfgline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plantmfgline, HttpStatus.OK);
    }

    /**
     * DELETE  /plantmfglines/:id -> delete the "id" plantmfgline.
     */
    @RequestMapping(value = "/plantmfglines/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Plantmfgline : {}", id);
        plantmfglineRepository.delete(id);
    }
}
