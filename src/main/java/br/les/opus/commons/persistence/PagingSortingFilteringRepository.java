package br.les.opus.commons.persistence;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.les.opus.commons.persistence.filtering.Filter;

public interface PagingSortingFilteringRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
	
	Page<T> findAll(Pageable pageable, Filter filter);
	
}
