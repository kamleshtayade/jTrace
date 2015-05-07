package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemmfrpart;
import com.spring.app.repository.ItemmfrpartRepository;
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
 * REST controller for managing Itemmfrpart.
 */
@RestController
@RequestMapping("/api")
public class ItemmfrpartResource {

    private final Logger log = LoggerFactory.getLogger(ItemmfrpartResource.class);

    @Inject
    private ItemmfrpartRepository itemmfrpartRepository;

    /**
     * POST  /itemmfrparts -> Create a new itemmfrpart.
     */
    @RequestMapping(value = "/itemmfrparts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Itemmfrpart itemmfrpart) throws URISyntaxException {
        log.debug("REST request to save Itemmfrpart : {}", itemmfrpart);
        if (itemmfrpart.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new itemmfrpart cannot already have an ID").build();
        }
        itemmfrpartRepository.save(itemmfrpart);
        return ResponseEntity.created(new URI("/api/itemmfrparts/" + itemmfrpart.getId())).build();
    }

    /**
     * PUT  /itemmfrparts -> Updates an existing itemmfrpart.
     */
    @RequestMapping(value = "/itemmfrparts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Itemmfrpart itemmfrpart) throws URISyntaxException {
        log.debug("REST request to update Itemmfrpart : {}", itemmfrpart);
        if (itemmfrpart.getId() == null) {
            return create(itemmfrpart);
        }
        itemmfrpartRepository.save(itemmfrpart);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /itemmfrparts -> get all the itemmfrparts.
     */
    @RequestMapping(value = "/itemmfrparts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Itemmfrpart>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Itemmfrpart> page = itemmfrpartRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/itemmfrparts", offset, limit);
        return new ResponseEntity<List<Itemmfrpart>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /itemmfrparts/:id -> get the "id" itemmfrpart.
     */
    @RequestMapping(value = "/itemmfrparts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Itemmfrpart> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Itemmfrpart : {}", id);
        Itemmfrpart itemmfrpart = itemmfrpartRepository.findOne(id);
        if (itemmfrpart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemmfrpart, HttpStatus.OK);
    }

    /**
     * DELETE  /itemmfrparts/:id -> delete the "id" itemmfrpart.
     */
    @RequestMapping(value = "/itemmfrparts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Itemmfrpart : {}", id);
        itemmfrpartRepository.delete(id);
    }
}
