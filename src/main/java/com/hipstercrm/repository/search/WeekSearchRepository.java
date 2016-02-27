package com.hipstercrm.repository.search;

import com.hipstercrm.domain.Week;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Week entity.
 */
public interface WeekSearchRepository extends ElasticsearchRepository<Week, Long> {
}
