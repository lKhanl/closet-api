package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.ShoesConverter;
import dev.oguzhanercelik.entity.Shoes;
import dev.oguzhanercelik.entity.Shoes;
import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.ShoesDto;
import dev.oguzhanercelik.model.request.ShoesFilterRequest;
import dev.oguzhanercelik.model.request.ShoesCreateRequest;
import dev.oguzhanercelik.repository.ShoesSpecification;
import dev.oguzhanercelik.repository.ShoesRepository;
import dev.oguzhanercelik.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoesService {

    private final ShoesRepository shoesRepository;
    private final ShoesConverter shoesConverter;

    public void create(Integer userId, ShoesCreateRequest request) {
        final Shoes shoes = shoesConverter.convertAsEntity(userId, request);
        shoesRepository.save(shoes);
    }

    public PagingResult<ShoesDto> getAllByFilterShoes(Integer id, ShoesFilterRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Shoes> specification = ShoesSpecification.getFilterQuery(id, request);
        final Page<Shoes> shoesPage = shoesRepository.findAll(specification, pageable);
        final List<ShoesDto> shoesDtoList = shoesPage.stream()
                .map(shoesConverter::convertAsDto)
                .toList();
        return new PagingResult<>(shoesDtoList,
                                  shoesPage.getTotalPages(),
                                  shoesPage.getTotalElements(),
                                  shoesPage.getSize(),
                                  shoesPage.getNumber(),
                                  shoesPage.isEmpty());
    }
}
