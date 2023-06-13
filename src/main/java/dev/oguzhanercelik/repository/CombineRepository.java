package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Combine;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CombineRepository extends PagingAndSortingRepository<Combine, Integer>, JpaSpecificationExecutor<Combine> {
    Optional<Combine> findByIdAndUserId(Integer id, Integer userId);

}
