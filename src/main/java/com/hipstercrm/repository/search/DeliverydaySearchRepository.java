package com.hipstercrm.repository.search;

import com.hipstercrm.domain.Deliveryday;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Deliveryday entity.
 */
public interface DeliverydaySearchRepository extends ElasticsearchRepository<Deliveryday, Long> {
}
