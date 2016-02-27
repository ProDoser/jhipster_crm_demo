package com.hipstercrm.repository.search;

import com.hipstercrm.domain.Fruitpack;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Fruitpack entity.
 */
public interface FruitpackSearchRepository extends ElasticsearchRepository<Fruitpack, Long> {
}
