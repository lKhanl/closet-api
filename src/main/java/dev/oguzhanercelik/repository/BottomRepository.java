package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Bottom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BottomRepository extends PagingAndSortingRepository<Bottom, Integer>, JpaSpecificationExecutor<Bottom> {
}
