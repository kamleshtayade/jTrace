package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Plantmfgline;
import com.spring.app.repository.PlantmfglineRepository;
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
    public ResponseEntity<Void> create(@Valid @RequestBody Plantmfgline plantmfgline) throws URISyntaxException {
        log.debug("REST request to save Plantmfgline : {}", plantmfgline);
        if (plantmfgline.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new plantmfgline cannot already have an ID").build();
        }
        plantmfglineRepository.save(plantmfgline);
        return ResponseEntity.created(new URI("/api/plantmfglines/" + plantmfgline.getId())).build();
    }

    /**
     * PUT  /plantmfglines -> Updates an existing plantmfgline.
     */
    @RequestMapping(value = "/plantmfglines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Plantmfgline plantmfgline) throws URISyntaxException {
        log.debug("REST request to update Plantmfgline : {}", plantmfgline);
        if (plantmfgline.getId() == null) {
            return create(plantmfgline);
        }
        plantmfglineRepository.save(plantmfgline);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /plantmfglines -> get all the plantmfglines.
     */
    @RequestMapping(value = "/plantmfglines",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Plantmfgline>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Plantmfgline> page = plantmfglineRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plantmfglines", offset, limit);
        return new ResponseEntity<List<Plantmfgline>>(page.getContent(), headers, HttpStatus.OK);
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
