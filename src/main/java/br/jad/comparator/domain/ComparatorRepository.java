package br.jad.comparator.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for ComparatorRecord
 */
@Repository
public interface ComparatorRepository extends CrudRepository<ComparatorRecord,ComparatorIdentity> {

    @Override
    @RestResource( exported = false )
    void deleteAll();
}
