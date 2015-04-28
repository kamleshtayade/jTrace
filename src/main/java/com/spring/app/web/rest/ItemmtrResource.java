package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemmtr;
import com.spring.app.repository.ItemmtrRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Itemmtr.
 */
@RestController
@RequestMapping("/api")
public class ItemmtrResource {

    private final Logger log = LoggerFactory.getLogger(ItemmtrResource.class);

    @Inject
    private ItemmtrRepository itemmtrRepository;

    /**
     * POST  /itemmtrs -> Create a new itemmtr.
     */
    @RequestMapping(value = "/itemmtrs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Itemmtr itemmtr) throws URISyntaxException {
        log.debug("REST request to save Itemmtr : {}", itemmtr);
        if (itemmtr.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new itemmtr cannot already have an ID").build();
        }
        itemmtrRepository.save(itemmtr);
        return ResponseEntity.created(new URI("/api/itemmtrs/" + itemmtr.getId())).build();
    }

    /**
     * PUT  /itemmtrs -> Updates an existing itemmtr.
     */
    @RequestMapping(value = "/itemmtrs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Itemmtr itemmtr) throws URISyntaxException {
        log.debug("REST request to update Itemmtr : {}", itemmtr);
        if (itemmtr.getId() == null) {
            return create(itemmtr);
        }
        itemmtrRepository.save(itemmtr);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /itemmtrs -> get all the itemmtrs.
     */
    @RequestMapping(value = "/itemmtrs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Itemmtr> getAll() {
        log.debug("REST request to get all Itemmtrs");
        return itemmtrRepository.findAll();
    }

    /**
     * GET  /itemmtrs/:id -> get the "id" itemmtr.
     */
    @RequestMapping(value = "/itemmtrs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Itemmtr> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Itemmtr : {}", id);
        Itemmtr itemmtr = itemmtrRepository.findOne(id);
        if (itemmtr == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemmtr, HttpStatus.OK);
    }

    /**
     * DELETE  /itemmtrs/:id -> delete the "id" itemmtr.
     */
    @RequestMapping(value = "/itemmtrs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Itemmtr : {}", id);
        itemmtrRepository.delete(id);
    }
}
