package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Bomheader;
import com.spring.app.repository.BomheaderRepository;
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
 * REST controller for managing Bomheader.
 */
@RestController
@RequestMapping("/api")
public class BomheaderResource {

    private final Logger log = LoggerFactory.getLogger(BomheaderResource.class);

    @Inject
    private BomheaderRepository bomheaderRepository;

    /**
     * POST  /bomheaders -> Create a new bomheader.
     */
    @RequestMapping(value = "/bomheaders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Bomheader bomheader) throws URISyntaxException {
        log.debug("REST request to save Bomheader : {}", bomheader);
        if (bomheader.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bomheader cannot already have an ID").build();
        }
        bomheaderRepository.save(bomheader);
        return ResponseEntity.created(new URI("/api/bomheaders/" + bomheader.getId())).build();
    }

    /**
     * PUT  /bomheaders -> Updates an existing bomheader.
     */
    @RequestMapping(value = "/bomheaders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Bomheader bomheader) throws URISyntaxException {
        log.debug("REST request to update Bomheader : {}", bomheader);
        if (bomheader.getId() == null) {
            return create(bomheader);
        }
        bomheaderRepository.save(bomheader);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bomheaders -> get all the bomheaders.
     */
    @RequestMapping(value = "/bomheaders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bomheader>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Bomheader> page = bomheaderRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bomheaders", offset, limit);
        return new ResponseEntity<List<Bomheader>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bomheaders/:id -> get the "id" bomheader.
     */
    @RequestMapping(value = "/bomheaders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bomheader> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Bomheader : {}", id);
        Bomheader bomheader = bomheaderRepository.findOne(id);
        if (bomheader == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bomheader, HttpStatus.OK);
    }

    /**
     * DELETE  /bomheaders/:id -> delete the "id" bomheader.
     */
    @RequestMapping(value = "/bomheaders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Bomheader : {}", id);
        bomheaderRepository.delete(id);
    }
}
