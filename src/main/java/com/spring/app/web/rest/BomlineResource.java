package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Bomline;
import com.spring.app.repository.BomlineRepository;
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
 * REST controller for managing Bomline.
 */
@RestController
@RequestMapping("/api")
public class BomlineResource {

    private final Logger log = LoggerFactory.getLogger(BomlineResource.class);

    @Inject
    private BomlineRepository bomlineRepository;

    /**
     * POST  /bomlines -> Create a new bomline.
     */
    @RequestMapping(value = "/bomlines",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Bomline bomline) throws URISyntaxException {
        log.debug("REST request to save Bomline : {}", bomline);
        if (bomline.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bomline cannot already have an ID").build();
        }
        bomlineRepository.save(bomline);
        return ResponseEntity.created(new URI("/api/bomlines/" + bomline.getId())).build();
    }

    /**
     * PUT  /bomlines -> Updates an existing bomline.
     */
    @RequestMapping(value = "/bomlines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Bomline bomline) throws URISyntaxException {
        log.debug("REST request to update Bomline : {}", bomline);
        if (bomline.getId() == null) {
            return create(bomline);
        }
        bomlineRepository.save(bomline);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bomlines -> get all the bomlines.
     */
    @RequestMapping(value = "/bomlines",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bomline>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Bomline> page = bomlineRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bomlines", offset, limit);
        return new ResponseEntity<List<Bomline>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bomlines/:id -> get the "id" bomline.
     */
    @RequestMapping(value = "/bomlines/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bomline> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Bomline : {}", id);
        Bomline bomline = bomlineRepository.findOne(id);
        if (bomline == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bomline, HttpStatus.OK);
    }

    /**
     * DELETE  /bomlines/:id -> delete the "id" bomline.
     */
    @RequestMapping(value = "/bomlines/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Bomline : {}", id);
        bomlineRepository.delete(id);
    }
}
