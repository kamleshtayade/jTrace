package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemctn;
import com.spring.app.repository.ItemctnRepository;
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
 * REST controller for managing Itemctn.
 */
@RestController
@RequestMapping("/api")
public class ItemctnResource {

    private final Logger log = LoggerFactory.getLogger(ItemctnResource.class);

    @Inject
    private ItemctnRepository itemctnRepository;

    /**
     * POST  /itemctns -> Create a new itemctn.
     */
    @RequestMapping(value = "/itemctns",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Itemctn itemctn) {
        log.debug("REST request to save Itemctn : {}", itemctn);
        itemctnRepository.save(itemctn);
    }

    /**
     * GET  /itemctns -> get all the itemctns.
     */
    @RequestMapping(value = "/itemctns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Itemctn> getAll() {
        log.debug("REST request to get all Itemctns");
        return itemctnRepository.findAll();
    }

    /**
     * GET  /itemctns/:id -> get the "id" itemctn.
     */
    @RequestMapping(value = "/itemctns/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Itemctn> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Itemctn : {}", id);
        Itemctn itemctn = itemctnRepository.findOne(id);
        if (itemctn == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemctn, HttpStatus.OK);
    }

    /**
     * DELETE  /itemctns/:id -> delete the "id" itemctn.
     */
    @RequestMapping(value = "/itemctns/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Itemctn : {}", id);
        itemctnRepository.delete(id);
    }
}
