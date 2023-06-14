package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Bottom;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BottomRepository extends PagingAndSortingRepository<Bottom, Integer>, JpaSpecificationExecutor<Bottom> {
    Optional<Bottom> findByIdAndUserId(Integer id, Integer userId);

    List<Bottom> findByUserId(Integer userId);

}
