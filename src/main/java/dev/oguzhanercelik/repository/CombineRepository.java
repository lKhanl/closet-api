package dev.oguzhanercelik.repository;

import dev.oguzhanercelik.entity.Combine;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CombineRepository extends PagingAndSortingRepository<Combine, Integer>, JpaSpecificationExecutor<Combine> {
    Optional<Combine> findByIdAndUserId(Integer id, Integer userId);

    void deleteByUserIdAndTopId(Integer userId, Integer topId);

    void deleteByUserIdAndBottomId(Integer userId, Integer bottomId);

    void deleteByUserIdAndShoesId(Integer userId, Integer shoesId);

    List<Combine> findByUserIdOrderByIdDesc(Integer userId);

}
