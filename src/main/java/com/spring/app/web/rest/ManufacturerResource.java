package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Manufacturer;
import com.spring.app.repository.ManufacturerRepository;
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
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/api")
public class ManufacturerResource {

    private final Logger log = LoggerFactory.getLogger(ManufacturerResource.class);

    @Inject
    private ManufacturerRepository manufacturerRepository;

    /**
     * POST  /manufacturers -> Create a new manufacturer.
     */
    @RequestMapping(value = "/manufacturers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Manufacturer manufacturer) {
        log.debug("REST request to save Manufacturer : {}", manufacturer);
        manufacturerRepository.save(manufacturer);
    }

    /**
     * GET  /manufacturers -> get all the manufacturers.
     */
    @RequestMapping(value = "/manufacturers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Manufacturer> getAll() {
        log.debug("REST request to get all Manufacturers");
        return manufacturerRepository.findAll();
    }

    /**
     * GET  /manufacturers/:id -> get the "id" manufacturer.
     */
    @RequestMapping(value = "/manufacturers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Manufacturer> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Manufacturer : {}", id);
        Manufacturer manufacturer = manufacturerRepository.findOne(id);
        if (manufacturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(manufacturer, HttpStatus.OK);
    }

    /**
     * DELETE  /manufacturers/:id -> delete the "id" manufacturer.
     */
    @RequestMapping(value = "/manufacturers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Manufacturer : {}", id);
        manufacturerRepository.delete(id);
    }
}
