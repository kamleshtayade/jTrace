package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemtype;
import com.spring.app.repository.ItemtypeRepository;
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
 * REST controller for managing Itemtype.
 */
@RestController
@RequestMapping("/api")
public class ItemtypeResource {

    private final Logger log = LoggerFactory.getLogger(ItemtypeResource.class);

    @Inject
    private ItemtypeRepository itemtypeRepository;

    /**
     * POST  /itemtypes -> Create a new itemtype.
     */
    @RequestMapping(value = "/itemtypes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Itemtype itemtype) throws URISyntaxException {
        log.debug("REST request to save Itemtype : {}", itemtype);
        if (itemtype.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new itemtype cannot already have an ID").build();
        }
        itemtypeRepository.save(itemtype);
        return ResponseEntity.created(new URI("/api/itemtypes/" + itemtype.getId())).build();
    }

    /**
     * PUT  /itemtypes -> Updates an existing itemtype.
     */
    @RequestMapping(value = "/itemtypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Itemtype itemtype) throws URISyntaxException {
        log.debug("REST request to update Itemtype : {}", itemtype);
        if (itemtype.getId() == null) {
            return create(itemtype);
        }
        itemtypeRepository.save(itemtype);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /itemtypes -> get all the itemtypes.
     */
    @RequestMapping(value = "/itemtypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Itemtype>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Itemtype> page = itemtypeRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/itemtypes", offset, limit);
        return new ResponseEntity<List<Itemtype>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /itemtypes/:id -> get the "id" itemtype.
     */
    @RequestMapping(value = "/itemtypes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Itemtype> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Itemtype : {}", id);
        Itemtype itemtype = itemtypeRepository.findOne(id);
        if (itemtype == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemtype, HttpStatus.OK);
    }

    /**
     * DELETE  /itemtypes/:id -> delete the "id" itemtype.
     */
    @RequestMapping(value = "/itemtypes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Itemtype : {}", id);
        itemtypeRepository.delete(id);
    }
}
