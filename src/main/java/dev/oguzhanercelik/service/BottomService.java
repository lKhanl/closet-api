package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.BottomConverter;
import dev.oguzhanercelik.entity.Bottom;
import dev.oguzhanercelik.entity.Top;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.BottomDto;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.BottomCreateRequest;
import dev.oguzhanercelik.model.request.BottomFilterRequest;
import dev.oguzhanercelik.model.request.BottomUpdateRequest;
import dev.oguzhanercelik.repository.BottomRepository;
import dev.oguzhanercelik.repository.BottomSpecification;
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
public class BottomService {

    private final BottomRepository bottomRepository;
    private final BottomConverter bottomConverter;

    public void create(Integer userId, BottomCreateRequest request) {
        final Bottom bottom = bottomConverter.convertAsEntity(userId, request);
        bottomRepository.save(bottom);
    }

    public PagingResult<BottomDto> getAllByFilterBottom(Integer id, BottomFilterRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Bottom> specification = BottomSpecification.getFilterQuery(id, request);
        final Page<Bottom> bottomPage = bottomRepository.findAll(specification, pageable);
        final List<BottomDto> bottomDtoList = bottomPage.stream()
                .map(bottomConverter::convertAsDto)
                .toList();
        return new PagingResult<>(bottomDtoList,
                                  bottomPage.getTotalPages(),
                                  bottomPage.getTotalElements(),
                                  bottomPage.getSize(),
                                  bottomPage.getNumber(),
                                  bottomPage.isEmpty());
    }

    public void update(Integer id, Integer userId, BottomUpdateRequest request) {
        final Optional<Bottom> optionalBottom = bottomRepository.findByIdAndUserId(id, userId);
        if (optionalBottom.isEmpty()) {
            throw new ApiException(ErrorEnum.VALIDATION_ERROR);
        }
        final Bottom bottom = optionalBottom.get();
        bottom.setName(request.getName());
        bottomRepository.save(bottom);
    }
}
