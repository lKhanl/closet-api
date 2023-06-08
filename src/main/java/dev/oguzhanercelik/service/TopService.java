package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.TopConverter;
import dev.oguzhanercelik.entity.Top;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.TopDto;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.TopCreateRequest;
import dev.oguzhanercelik.model.request.TopFilterRequest;
import dev.oguzhanercelik.model.request.TopUpdateRequest;
import dev.oguzhanercelik.repository.TopRepository;
import dev.oguzhanercelik.repository.TopSpecification;
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
public class TopService {

    private final TopRepository topRepository;
    private final TopConverter topConverter;

    public void create(Integer userId, TopCreateRequest request) {
        final Top top = topConverter.convertAsEntity(userId, request);
        topRepository.save(top);
    }

    public PagingResult<TopDto> getAllByFilterTop(Integer userId, TopFilterRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Top> specification = TopSpecification.getFilterQuery(userId, request);
        final Page<Top> merchantProductPage = topRepository.findAll(specification, pageable);
        final List<TopDto> merchantProductDtoList = merchantProductPage.stream()
                .map(topConverter::convertAsDto)
                .toList();
        return new PagingResult<>(merchantProductDtoList,
                                  merchantProductPage.getTotalPages(),
                                  merchantProductPage.getTotalElements(),
                                  merchantProductPage.getSize(),
                                  merchantProductPage.getNumber(),
                                  merchantProductPage.isEmpty());
    }

    public void update(Integer id, Integer userId, TopUpdateRequest request) {
        final Optional<Top> optionalTop = topRepository.findByIdAndUserId(id, userId);
        if (optionalTop.isEmpty()) {
            throw new ApiException(ErrorEnum.TOP_NOT_FOUND);
        }
        final Top top = optionalTop.get();
        top.setName(request.getName());
        topRepository.save(top);
    }
}
