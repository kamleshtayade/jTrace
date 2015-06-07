package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Domheader;
import com.spring.app.repository.DomheaderRepository;
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
 * REST controller for managing Domheader.
 */
@RestController
@RequestMapping("/api")
public class DomheaderResource {

    private final Logger log = LoggerFactory.getLogger(DomheaderResource.class);

    @Inject
    private DomheaderRepository domheaderRepository;

    /**
     * POST  /domheaders -> Create a new domheader.
     */
    @RequestMapping(value = "/domheaders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Domheader domheader) throws URISyntaxException {
        log.debug("REST request to save Domheader : {}", domheader);
        if (domheader.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new domheader cannot already have an ID").build();
        }
        domheaderRepository.save(domheader);
        return ResponseEntity.created(new URI("/api/domheaders/" + domheader.getId())).build();
    }

    /**
     * PUT  /domheaders -> Updates an existing domheader.
     */
    @RequestMapping(value = "/domheaders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Domheader domheader) throws URISyntaxException {
        log.debug("REST request to update Domheader : {}", domheader);
        if (domheader.getId() == null) {
            return create(domheader);
        }
        domheaderRepository.save(domheader);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /domheaders -> get all the domheaders.
     */
    @RequestMapping(value = "/domheaders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Domheader>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Domheader> page = domheaderRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/domheaders", offset, limit);
        return new ResponseEntity<List<Domheader>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /domheaders/:id -> get the "id" domheader.
     */
    @RequestMapping(value = "/domheaders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Domheader> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Domheader : {}", id);
        Domheader domheader = domheaderRepository.findOne(id);
        if (domheader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(domheader, HttpStatus.OK);
    }

    /**
     * DELETE  /domheaders/:id -> delete the "id" domheader.
     */
    @RequestMapping(value = "/domheaders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Domheader : {}", id);
        domheaderRepository.delete(id);
    }
}
