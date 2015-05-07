package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Itemctn;
import com.spring.app.repository.ItemctnRepository;
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
    public ResponseEntity<Void> create(@Valid @RequestBody Itemctn itemctn) throws URISyntaxException {
        log.debug("REST request to save Itemctn : {}", itemctn);
        if (itemctn.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new itemctn cannot already have an ID").build();
        }
        itemctnRepository.save(itemctn);
        return ResponseEntity.created(new URI("/api/itemctns/" + itemctn.getId())).build();
    }

    /**
     * PUT  /itemctns -> Updates an existing itemctn.
     */
    @RequestMapping(value = "/itemctns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Itemctn itemctn) throws URISyntaxException {
        log.debug("REST request to update Itemctn : {}", itemctn);
        if (itemctn.getId() == null) {
            return create(itemctn);
        }
        itemctnRepository.save(itemctn);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /itemctns -> get all the itemctns.
     */
    @RequestMapping(value = "/itemctns",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Itemctn>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Itemctn> page = itemctnRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/itemctns", offset, limit);
        return new ResponseEntity<List<Itemctn>>(page.getContent(), headers, HttpStatus.OK);
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
