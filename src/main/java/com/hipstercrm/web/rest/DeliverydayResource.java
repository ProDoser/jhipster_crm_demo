package com.hipstercrm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hipstercrm.domain.Deliveryday;
import com.hipstercrm.repository.DeliverydayRepository;
import com.hipstercrm.repository.search.DeliverydaySearchRepository;
import com.hipstercrm.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Deliveryday.
 */
@RestController
@RequestMapping("/api")
public class DeliverydayResource {

    private final Logger log = LoggerFactory.getLogger(DeliverydayResource.class);
        
    @Inject
    private DeliverydayRepository deliverydayRepository;
    
    @Inject
    private DeliverydaySearchRepository deliverydaySearchRepository;
    
    /**
     * POST  /deliverydays -> Create a new deliveryday.
     */
    @RequestMapping(value = "/deliverydays",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Deliveryday> createDeliveryday(@Valid @RequestBody Deliveryday deliveryday) throws URISyntaxException {
        log.debug("REST request to save Deliveryday : {}", deliveryday);
        if (deliveryday.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("deliveryday", "idexists", "A new deliveryday cannot already have an ID")).body(null);
        }
        Deliveryday result = deliverydayRepository.save(deliveryday);
        deliverydaySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/deliverydays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deliveryday", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deliverydays -> Updates an existing deliveryday.
     */
    @RequestMapping(value = "/deliverydays",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Deliveryday> updateDeliveryday(@Valid @RequestBody Deliveryday deliveryday) throws URISyntaxException {
        log.debug("REST request to update Deliveryday : {}", deliveryday);
        if (deliveryday.getId() == null) {
            return createDeliveryday(deliveryday);
        }
        Deliveryday result = deliverydayRepository.save(deliveryday);
        deliverydaySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deliveryday", deliveryday.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deliverydays -> get all the deliverydays.
     */
    @RequestMapping(value = "/deliverydays",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Deliveryday> getAllDeliverydays() {
        log.debug("REST request to get all Deliverydays");
        return deliverydayRepository.findAll();
            }

    /**
     * GET  /deliverydays/:id -> get the "id" deliveryday.
     */
    @RequestMapping(value = "/deliverydays/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Deliveryday> getDeliveryday(@PathVariable Long id) {
        log.debug("REST request to get Deliveryday : {}", id);
        Deliveryday deliveryday = deliverydayRepository.findOne(id);
        return Optional.ofNullable(deliveryday)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /deliverydays/:id -> delete the "id" deliveryday.
     */
    @RequestMapping(value = "/deliverydays/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeliveryday(@PathVariable Long id) {
        log.debug("REST request to delete Deliveryday : {}", id);
        deliverydayRepository.delete(id);
        deliverydaySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deliveryday", id.toString())).build();
    }

    /**
     * SEARCH  /_search/deliverydays/:query -> search for the deliveryday corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/deliverydays/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Deliveryday> searchDeliverydays(@PathVariable String query) {
        log.debug("REST request to search Deliverydays for query {}", query);
        return StreamSupport
            .stream(deliverydaySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
