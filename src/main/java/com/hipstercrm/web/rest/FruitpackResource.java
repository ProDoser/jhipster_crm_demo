package com.hipstercrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hipstercrm.domain.Fruitpack;
import com.hipstercrm.repository.FruitpackRepository;
import com.hipstercrm.repository.search.FruitpackSearchRepository;
import com.hipstercrm.web.rest.util.HeaderUtil;
import com.hipstercrm.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Fruitpack.
 */
@RestController
@RequestMapping("/api")
public class FruitpackResource {

    private final Logger log = LoggerFactory.getLogger(FruitpackResource.class);
        
    @Inject
    private FruitpackRepository fruitpackRepository;
    
    @Inject
    private FruitpackSearchRepository fruitpackSearchRepository;
    
    /**
     * POST  /fruitpacks -> Create a new fruitpack.
     */
    @RequestMapping(value = "/fruitpacks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fruitpack> createFruitpack(@Valid @RequestBody Fruitpack fruitpack) throws URISyntaxException {
        log.debug("REST request to save Fruitpack : {}", fruitpack);
        if (fruitpack.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fruitpack", "idexists", "A new fruitpack cannot already have an ID")).body(null);
        }
        Fruitpack result = fruitpackRepository.save(fruitpack);
        fruitpackSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fruitpacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fruitpack", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fruitpacks -> Updates an existing fruitpack.
     */
    @RequestMapping(value = "/fruitpacks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fruitpack> updateFruitpack(@Valid @RequestBody Fruitpack fruitpack) throws URISyntaxException {
        log.debug("REST request to update Fruitpack : {}", fruitpack);
        if (fruitpack.getId() == null) {
            return createFruitpack(fruitpack);
        }
        Fruitpack result = fruitpackRepository.save(fruitpack);
        fruitpackSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fruitpack", fruitpack.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fruitpacks -> get all the fruitpacks.
     */
    @RequestMapping(value = "/fruitpacks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Fruitpack>> getAllFruitpacks(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Fruitpacks");
        Page<Fruitpack> page = fruitpackRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fruitpacks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fruitpacks/:id -> get the "id" fruitpack.
     */
    @RequestMapping(value = "/fruitpacks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fruitpack> getFruitpack(@PathVariable Long id) {
        log.debug("REST request to get Fruitpack : {}", id);
        Fruitpack fruitpack = fruitpackRepository.findOne(id);
        return Optional.ofNullable(fruitpack)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fruitpacks/:id -> delete the "id" fruitpack.
     */
    @RequestMapping(value = "/fruitpacks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFruitpack(@PathVariable Long id) {
        log.debug("REST request to delete Fruitpack : {}", id);
        fruitpackRepository.delete(id);
        fruitpackSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fruitpack", id.toString())).build();
    }

    /**
     * SEARCH  /_search/fruitpacks/:query -> search for the fruitpack corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/fruitpacks/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fruitpack> searchFruitpacks(@PathVariable String query) {
        log.debug("REST request to search Fruitpacks for query {}", query);
        return StreamSupport
            .stream(fruitpackSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
