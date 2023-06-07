package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Combine;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CombineRepository extends PagingAndSortingRepository<Combine, Integer>, JpaSpecificationExecutor<Combine> {
}
