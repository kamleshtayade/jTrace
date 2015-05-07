package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Workorderline;
import com.spring.app.repository.WorkorderlineRepository;
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
 * REST controller for managing Workorderline.
 */
@RestController
@RequestMapping("/api")
public class WorkorderlineResource {

    private final Logger log = LoggerFactory.getLogger(WorkorderlineResource.class);

    @Inject
    private WorkorderlineRepository workorderlineRepository;

    /**
     * POST  /workorderlines -> Create a new workorderline.
     */
    @RequestMapping(value = "/workorderlines",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Workorderline workorderline) throws URISyntaxException {
        log.debug("REST request to save Workorderline : {}", workorderline);
        if (workorderline.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new workorderline cannot already have an ID").build();
        }
        workorderlineRepository.save(workorderline);
        return ResponseEntity.created(new URI("/api/workorderlines/" + workorderline.getId())).build();
    }

    /**
     * PUT  /workorderlines -> Updates an existing workorderline.
     */
    @RequestMapping(value = "/workorderlines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Workorderline workorderline) throws URISyntaxException {
        log.debug("REST request to update Workorderline : {}", workorderline);
        if (workorderline.getId() == null) {
            return create(workorderline);
        }
        workorderlineRepository.save(workorderline);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /workorderlines -> get all the workorderlines.
     */
    @RequestMapping(value = "/workorderlines",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Workorderline>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Workorderline> page = workorderlineRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workorderlines", offset, limit);
        return new ResponseEntity<List<Workorderline>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /workorderlines/:id -> get the "id" workorderline.
     */
    @RequestMapping(value = "/workorderlines/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Workorderline> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Workorderline : {}", id);
        Workorderline workorderline = workorderlineRepository.findOne(id);
        if (workorderline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workorderline, HttpStatus.OK);
    }

    /**
     * DELETE  /workorderlines/:id -> delete the "id" workorderline.
     */
    @RequestMapping(value = "/workorderlines/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Workorderline : {}", id);
        workorderlineRepository.delete(id);
    }
}
