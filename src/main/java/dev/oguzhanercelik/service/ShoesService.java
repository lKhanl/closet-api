package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.ShoesConverter;
import dev.oguzhanercelik.entity.Shoes;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.ShoesDto;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.ShoesCreateRequest;
import dev.oguzhanercelik.model.request.ShoesFilterRequest;
import dev.oguzhanercelik.model.request.ShoesUpdateRequest;
import dev.oguzhanercelik.repository.ShoesRepository;
import dev.oguzhanercelik.repository.ShoesSpecification;
import dev.oguzhanercelik.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void update(Integer id, Integer userId, ShoesUpdateRequest request) {
        final Optional<Shoes> optionalShoes = shoesRepository.findByIdAndUserId(id, userId);
        if (optionalShoes.isEmpty()) {
            throw new ApiException(ErrorEnum.SHOES_NOT_FOUND);
        }
        final Shoes shoes = optionalShoes.get();
        shoes.setName(request.getName());
        shoesRepository.save(shoes);
    }
}
