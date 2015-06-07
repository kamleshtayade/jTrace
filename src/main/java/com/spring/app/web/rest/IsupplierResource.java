package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Isupplier;
import com.spring.app.repository.IsupplierRepository;
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
 * REST controller for managing Isupplier.
 */
@RestController
@RequestMapping("/api")
public class IsupplierResource {

    private final Logger log = LoggerFactory.getLogger(IsupplierResource.class);

    @Inject
    private IsupplierRepository isupplierRepository;

    /**
     * POST  /isuppliers -> Create a new isupplier.
     */
    @RequestMapping(value = "/isuppliers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Isupplier isupplier) throws URISyntaxException {
        log.debug("REST request to save Isupplier : {}", isupplier);
        if (isupplier.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new isupplier cannot already have an ID").build();
        }
        isupplierRepository.save(isupplier);
        return ResponseEntity.created(new URI("/api/isuppliers/" + isupplier.getId())).build();
    }

    /**
     * PUT  /isuppliers -> Updates an existing isupplier.
     */
    @RequestMapping(value = "/isuppliers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Isupplier isupplier) throws URISyntaxException {
        log.debug("REST request to update Isupplier : {}", isupplier);
        if (isupplier.getId() == null) {
            return create(isupplier);
        }
        isupplierRepository.save(isupplier);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /isuppliers -> get all the isuppliers.
     */
    @RequestMapping(value = "/isuppliers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Isupplier>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Isupplier> page = isupplierRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/isuppliers", offset, limit);
        return new ResponseEntity<List<Isupplier>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /isuppliers/:id -> get the "id" isupplier.
     */
    @RequestMapping(value = "/isuppliers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Isupplier> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Isupplier : {}", id);
        Isupplier isupplier = isupplierRepository.findOneWithEagerRelationships(id);
        if (isupplier == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(isupplier, HttpStatus.OK);
    }

    /**
     * DELETE  /isuppliers/:id -> delete the "id" isupplier.
     */
    @RequestMapping(value = "/isuppliers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Isupplier : {}", id);
        isupplierRepository.delete(id);
    }
}
