package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Manufacturer;
import com.spring.app.repository.ManufacturerRepository;
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
    public ResponseEntity<Void> create(@Valid @RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to save Manufacturer : {}", manufacturer);
        if (manufacturer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new manufacturer cannot already have an ID").build();
        }
        manufacturerRepository.save(manufacturer);
        return ResponseEntity.created(new URI("/api/manufacturers/" + manufacturer.getId())).build();
    }

    /**
     * PUT  /manufacturers -> Updates an existing manufacturer.
     */
    @RequestMapping(value = "/manufacturers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Manufacturer manufacturer) throws URISyntaxException {
        log.debug("REST request to update Manufacturer : {}", manufacturer);
        if (manufacturer.getId() == null) {
            return create(manufacturer);
        }
        manufacturerRepository.save(manufacturer);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /manufacturers -> get all the manufacturers.
     */
    @RequestMapping(value = "/manufacturers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Manufacturer>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Manufacturer> page = manufacturerRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/manufacturers", offset, limit);
        return new ResponseEntity<List<Manufacturer>>(page.getContent(), headers, HttpStatus.OK);
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
