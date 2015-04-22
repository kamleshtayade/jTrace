package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Plant;
import com.spring.app.repository.PlantRepository;
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
 * REST controller for managing Plant.
 */
@RestController
@RequestMapping("/api")
public class PlantResource {

    private final Logger log = LoggerFactory.getLogger(PlantResource.class);

    @Inject
    private PlantRepository plantRepository;

    /**
     * POST  /plants -> Create a new plant.
     */
    @RequestMapping(value = "/plants",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Plant plant) {
        log.debug("REST request to save Plant : {}", plant);
        plantRepository.save(plant);
    }

    /**
     * GET  /plants -> get all the plants.
     */
    @RequestMapping(value = "/plants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Plant> getAll() {
        log.debug("REST request to get all Plants");
        return plantRepository.findAll();
    }

    /**
     * GET  /plants/:id -> get the "id" plant.
     */
    @RequestMapping(value = "/plants/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plant> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Plant : {}", id);
        Plant plant = plantRepository.findOne(id);
        if (plant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plant, HttpStatus.OK);
    }

    /**
     * DELETE  /plants/:id -> delete the "id" plant.
     */
    @RequestMapping(value = "/plants/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Plant : {}", id);
        plantRepository.delete(id);
    }
}
