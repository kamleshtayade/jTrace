package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Domline;
import com.spring.app.repository.DomlineRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Domline.
 */
@RestController
@RequestMapping("/api")
public class DomlineResource {

    private final Logger log = LoggerFactory.getLogger(DomlineResource.class);

    @Inject
    private DomlineRepository domlineRepository;

    /**
     * POST  /domlines -> Create a new domline.
     */
    @RequestMapping(value = "/domlines",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Domline domline) throws URISyntaxException {
        log.debug("REST request to save Domline : {}", domline);
        if (domline.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new domline cannot already have an ID").build();
        }
        domlineRepository.save(domline);
        return ResponseEntity.created(new URI("/api/domlines/" + domline.getId())).build();
    }

    /**
     * PUT  /domlines -> Updates an existing domline.
     */
    @RequestMapping(value = "/domlines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Domline domline) throws URISyntaxException {
        log.debug("REST request to update Domline : {}", domline);
        if (domline.getId() == null) {
            return create(domline);
        }
        domlineRepository.save(domline);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /domlines -> get all the domlines.
     */
    @RequestMapping(value = "/domlines",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Domline>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Domline> page = domlineRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/domlines", offset, limit);
        return new ResponseEntity<List<Domline>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /domlines/:id -> get the "id" domline.
     */
    @RequestMapping(value = "/domlines/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domline> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Domline : {}", id);
        Domline domline = domlineRepository.findOne(id);
        if (domline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(domline, HttpStatus.OK);
    }

    /**
     * DELETE  /domlines/:id -> delete the "id" domline.
     */
    @RequestMapping(value = "/domlines/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Domline : {}", id);
        domlineRepository.delete(id);
    }
    
    /**
     * GET Report /domlines/:id -> get the "id" domline.
     */
    @RequestMapping(value = "/reportdomlines/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domline> get(@PathVariable String id, HttpServletResponse response) {
        log.debug("REST request to get Domline : {}", id);
        Domline domline = domlineRepository.findBySerialno(id);
        if (domline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(domline, HttpStatus.OK);
    }
}
