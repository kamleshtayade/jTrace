package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemmaster;
import com.spring.app.repository.ItemmasterRepository;
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
 * REST controller for managing Itemmaster.
 */
@RestController
@RequestMapping("/api")
public class ItemmasterResource {

    private final Logger log = LoggerFactory.getLogger(ItemmasterResource.class);

    @Inject
    private ItemmasterRepository itemmasterRepository;

    /**
     * POST  /itemmasters -> Create a new itemmaster.
     */
    @RequestMapping(value = "/itemmasters",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Itemmaster itemmaster) {
        log.debug("REST request to save Itemmaster : {}", itemmaster);
        itemmasterRepository.save(itemmaster);
    }

    /**
     * GET  /itemmasters -> get all the itemmasters.
     */
    @RequestMapping(value = "/itemmasters",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Itemmaster> getAll() {
        log.debug("REST request to get all Itemmasters");
        return itemmasterRepository.findAll();
    }

    /**
     * GET  /itemmasters/:id -> get the "id" itemmaster.
     */
    @RequestMapping(value = "/itemmasters/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Itemmaster> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Itemmaster : {}", id);
        Itemmaster itemmaster = itemmasterRepository.findOne(id);
        if (itemmaster == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemmaster, HttpStatus.OK);
    }

    /**
     * DELETE  /itemmasters/:id -> delete the "id" itemmaster.
     */
    @RequestMapping(value = "/itemmasters/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Itemmaster : {}", id);
        itemmasterRepository.delete(id);
    }
}
