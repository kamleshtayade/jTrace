package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemmfrpart;
import com.spring.app.repository.ItemmfrpartRepository;
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
 * REST controller for managing Itemmfrpart.
 */
@RestController
@RequestMapping("/api")
public class ItemmfrpartResource {

    private final Logger log = LoggerFactory.getLogger(ItemmfrpartResource.class);

    @Inject
    private ItemmfrpartRepository itemmfrpartRepository;

    /**
     * POST  /itemmfrparts -> Create a new itemmfrpart.
     */
    @RequestMapping(value = "/itemmfrparts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Itemmfrpart itemmfrpart) {
        log.debug("REST request to save Itemmfrpart : {}", itemmfrpart);
        itemmfrpartRepository.save(itemmfrpart);
    }

    /**
     * GET  /itemmfrparts -> get all the itemmfrparts.
     */
    @RequestMapping(value = "/itemmfrparts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Itemmfrpart> getAll() {
        log.debug("REST request to get all Itemmfrparts");
        return itemmfrpartRepository.findAll();
    }

    /**
     * GET  /itemmfrparts/:id -> get the "id" itemmfrpart.
     */
    @RequestMapping(value = "/itemmfrparts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Itemmfrpart> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Itemmfrpart : {}", id);
        Itemmfrpart itemmfrpart = itemmfrpartRepository.findOne(id);
        if (itemmfrpart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemmfrpart, HttpStatus.OK);
    }

    /**
     * DELETE  /itemmfrparts/:id -> delete the "id" itemmfrpart.
     */
    @RequestMapping(value = "/itemmfrparts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Itemmfrpart : {}", id);
        itemmfrpartRepository.delete(id);
    }
}
