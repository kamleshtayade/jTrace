package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Plantmc;
import com.spring.app.repository.PlantmcRepository;
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
 * REST controller for managing Plantmc.
 */
@RestController
@RequestMapping("/api")
public class PlantmcResource {

    private final Logger log = LoggerFactory.getLogger(PlantmcResource.class);

    @Inject
    private PlantmcRepository plantmcRepository;

    /**
     * POST  /plantmcs -> Create a new plantmc.
     */
    @RequestMapping(value = "/plantmcs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Plantmc plantmc) throws URISyntaxException {
        log.debug("REST request to save Plantmc : {}", plantmc);
        if (plantmc.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new plantmc cannot already have an ID").build();
        }
        plantmcRepository.save(plantmc);
        return ResponseEntity.created(new URI("/api/plantmcs/" + plantmc.getId())).build();
    }

    /**
     * PUT  /plantmcs -> Updates an existing plantmc.
     */
    @RequestMapping(value = "/plantmcs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Plantmc plantmc) throws URISyntaxException {
        log.debug("REST request to update Plantmc : {}", plantmc);
        if (plantmc.getId() == null) {
            return create(plantmc);
        }
        plantmcRepository.save(plantmc);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /plantmcs -> get all the plantmcs.
     */
    @RequestMapping(value = "/plantmcs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Plantmc>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Plantmc> page = plantmcRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plantmcs", offset, limit);
        return new ResponseEntity<List<Plantmc>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plantmcs/:id -> get the "id" plantmc.
     */
    @RequestMapping(value = "/plantmcs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plantmc> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Plantmc : {}", id);
        Plantmc plantmc = plantmcRepository.findOne(id);
        if (plantmc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plantmc, HttpStatus.OK);
    }

    /**
     * DELETE  /plantmcs/:id -> delete the "id" plantmc.
     */
    @RequestMapping(value = "/plantmcs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Plantmc : {}", id);
        plantmcRepository.delete(id);
    }
}
