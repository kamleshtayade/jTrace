package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Plant;
import com.spring.app.repository.PlantRepository;
import com.spring.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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
    public ResponseEntity<Void> create(@Valid @RequestBody Plant plant) throws URISyntaxException {
        log.debug("REST request to save Plant : {}", plant);
        if (plant.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new plant cannot already have an ID").build();
        }
        plantRepository.save(plant);
        return ResponseEntity.created(new URI("/api/plants/" + plant.getId())).build();
    }

    /**
     * PUT  /plants -> Updates an existing plant.
     */
    @RequestMapping(value = "/plants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Plant plant) throws URISyntaxException {
        log.debug("REST request to update Plant : {}", plant);
        if (plant.getId() == null) {
            return create(plant);
        }
        plantRepository.save(plant);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /plants -> get all the plants.
     */
    @RequestMapping(value = "/plants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Plant>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Plant> page = plantRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plants", offset, limit);
        return new ResponseEntity<List<Plant>>(page.getContent(), headers, HttpStatus.OK);
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
