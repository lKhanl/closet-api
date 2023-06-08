package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Shoes;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ShoesRepository extends PagingAndSortingRepository<Shoes, Integer>, JpaSpecificationExecutor<Shoes> {
    Optional<Shoes> findByIdAndUserId(Integer id, Integer userId);

}
