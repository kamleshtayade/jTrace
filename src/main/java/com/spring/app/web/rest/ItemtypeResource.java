package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemtype;
import com.spring.app.repository.ItemtypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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
    public void create(@RequestBody Itemtype itemtype) {
        log.debug("REST request to save Itemtype : {}", itemtype);
        itemtypeRepository.save(itemtype);
    }

    /**
     * GET  /itemtypes -> get all the itemtypes.
     */
    @RequestMapping(value = "/itemtypes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Itemtype> getAll() {
        log.debug("REST request to get all Itemtypes");
        return itemtypeRepository.findAll();
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
