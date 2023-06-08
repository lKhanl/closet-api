package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Top;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TopRepository extends PagingAndSortingRepository<Top, Integer>, JpaSpecificationExecutor<Top> {

    Optional<Top> findByIdAndUserId(Integer id, Integer userId);

}
