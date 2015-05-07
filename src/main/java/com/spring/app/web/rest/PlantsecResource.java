package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Plantsec;
import com.spring.app.repository.PlantsecRepository;
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
 * REST controller for managing Plantsec.
 */
@RestController
@RequestMapping("/api")
public class PlantsecResource {

    private final Logger log = LoggerFactory.getLogger(PlantsecResource.class);

    @Inject
    private PlantsecRepository plantsecRepository;

    /**
     * POST  /plantsecs -> Create a new plantsec.
     */
    @RequestMapping(value = "/plantsecs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Plantsec plantsec) throws URISyntaxException {
        log.debug("REST request to save Plantsec : {}", plantsec);
        if (plantsec.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new plantsec cannot already have an ID").build();
        }
        plantsecRepository.save(plantsec);
        return ResponseEntity.created(new URI("/api/plantsecs/" + plantsec.getId())).build();
    }

    /**
     * PUT  /plantsecs -> Updates an existing plantsec.
     */
    @RequestMapping(value = "/plantsecs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Plantsec plantsec) throws URISyntaxException {
        log.debug("REST request to update Plantsec : {}", plantsec);
        if (plantsec.getId() == null) {
            return create(plantsec);
        }
        plantsecRepository.save(plantsec);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /plantsecs -> get all the plantsecs.
     */
    @RequestMapping(value = "/plantsecs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Plantsec>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Plantsec> page = plantsecRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plantsecs", offset, limit);
        return new ResponseEntity<List<Plantsec>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /plantsecs/:id -> get the "id" plantsec.
     */
    @RequestMapping(value = "/plantsecs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Plantsec> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Plantsec : {}", id);
        Plantsec plantsec = plantsecRepository.findOne(id);
        if (plantsec == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(plantsec, HttpStatus.OK);
    }

    /**
     * DELETE  /plantsecs/:id -> delete the "id" plantsec.
     */
    @RequestMapping(value = "/plantsecs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Plantsec : {}", id);
        plantsecRepository.delete(id);
    }
}
