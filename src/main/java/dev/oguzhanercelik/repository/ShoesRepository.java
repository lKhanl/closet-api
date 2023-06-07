package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Shoes;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoesRepository extends PagingAndSortingRepository<Shoes, Integer>, JpaSpecificationExecutor<Shoes> {
}
