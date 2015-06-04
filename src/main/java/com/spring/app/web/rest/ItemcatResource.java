package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemcat;
import com.spring.app.repository.ItemcatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Itemcat.
 */
@RestController
@RequestMapping("/api")
public class ItemcatResource {

    private final Logger log = LoggerFactory.getLogger(ItemcatResource.class);

    @Inject
    private ItemcatRepository itemcatRepository;

    /**
     * POST  /itemcats -> Create a new itemcat.
     */
    @RequestMapping(value = "/itemcats",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Itemcat itemcat) throws URISyntaxException {
        log.debug("REST request to save Itemcat : {}", itemcat);
        if (itemcat.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new itemcat cannot already have an ID").build();
        }
        itemcatRepository.save(itemcat);
        log.debug("ItemCat Id to newly persisted Itemcats:"+itemcat.getId()+":");
        return ResponseEntity.created(new URI("/api/itemcats/" + itemcat.getId())).build();
    }

    /**
     * PUT  /itemcats -> Updates an existing itemcat.
     */
    @RequestMapping(value = "/itemcats",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Itemcat itemcat) throws URISyntaxException {
        log.debug("REST request to update Itemcat : {}", itemcat);
        if (itemcat.getId() == null) {
            return create(itemcat);
        }
        itemcatRepository.save(itemcat);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /itemcats -> get all the itemcats.
     */
    @RequestMapping(value = "/itemcats",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Itemcat> getAll() {
        log.debug("REST request to get all Itemcats");
        return itemcatRepository.findAll();
    }

    /**
     * GET  /itemcats/:id -> get the "id" itemcat.
     */
    @RequestMapping(value = "/itemcats/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Itemcat> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Itemcat : {}", id);
        Itemcat itemcat = itemcatRepository.findOne(id);
        if (itemcat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(itemcat, HttpStatus.OK);
    }

    /**
     * DELETE  /itemcats/:id -> delete the "id" itemcat.
     */
    @RequestMapping(value = "/itemcats/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Itemcat : {}", id);
        itemcatRepository.delete(id);
    }
}
