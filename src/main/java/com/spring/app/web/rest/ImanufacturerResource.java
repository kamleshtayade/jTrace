package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Imanufacturer;
import com.spring.app.repository.ImanufacturerRepository;
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
 * REST controller for managing Imanufacturer.
 */
@RestController
@RequestMapping("/api")
public class ImanufacturerResource {

    private final Logger log = LoggerFactory.getLogger(ImanufacturerResource.class);

    @Inject
    private ImanufacturerRepository imanufacturerRepository;

    /**
     * POST  /imanufacturers -> Create a new imanufacturer.
     */
    @RequestMapping(value = "/imanufacturers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Imanufacturer imanufacturer) throws URISyntaxException {
        log.debug("REST request to save Imanufacturer : {}", imanufacturer);
        if (imanufacturer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new imanufacturer cannot already have an ID").build();
        }
        imanufacturerRepository.save(imanufacturer);
        return ResponseEntity.created(new URI("/api/imanufacturers/" + imanufacturer.getId())).build();
    }

    /**
     * PUT  /imanufacturers -> Updates an existing imanufacturer.
     */
    @RequestMapping(value = "/imanufacturers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Imanufacturer imanufacturer) throws URISyntaxException {
        log.debug("REST request to update Imanufacturer : {}", imanufacturer);
        if (imanufacturer.getId() == null) {
            return create(imanufacturer);
        }
        imanufacturerRepository.save(imanufacturer);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /imanufacturers -> get all the imanufacturers.
     */
    @RequestMapping(value = "/imanufacturers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Imanufacturer>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Imanufacturer> page = imanufacturerRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/imanufacturers", offset, limit);
        return new ResponseEntity<List<Imanufacturer>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /imanufacturers/:id -> get the "id" imanufacturer.
     */
    @RequestMapping(value = "/imanufacturers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Imanufacturer> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Imanufacturer : {}", id);
        Imanufacturer imanufacturer = imanufacturerRepository.findOne(id);
        if (imanufacturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(imanufacturer, HttpStatus.OK);
    }

    /**
     * DELETE  /imanufacturers/:id -> delete the "id" imanufacturer.
     */
    @RequestMapping(value = "/imanufacturers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Imanufacturer : {}", id);
        imanufacturerRepository.delete(id);
    }
}
