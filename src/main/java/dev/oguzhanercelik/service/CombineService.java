package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.CombineConverter;
import dev.oguzhanercelik.entity.Combine;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.CombineDto;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.CombineCreateRequest;
import dev.oguzhanercelik.model.request.CombineFilterRequest;
import dev.oguzhanercelik.model.request.CombineUpdateRequest;
import dev.oguzhanercelik.repository.CombineRepository;
import dev.oguzhanercelik.repository.CombineSpecification;
import dev.oguzhanercelik.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CombineService {

    private final CombineRepository combineRepository;
    private final CombineConverter combineConverter;

    public void create(Integer userId, CombineCreateRequest request) {
        final Combine combine = combineConverter.convertAsEntity(userId, request);
        combineRepository.save(combine);
    }

    public PagingResult<CombineDto> getAllByFilterCombine(Integer id, CombineFilterRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Combine> specification = CombineSpecification.getFilterQuery(id, request);
        final Page<Combine> combinePage = combineRepository.findAll(specification, pageable);
        final List<CombineDto> combineDtoList = combinePage.stream()
                .map(combineConverter::convertAsDto)
                .toList();
        return new PagingResult<>(combineDtoList,
                combinePage.getTotalPages(),
                combinePage.getTotalElements(),
                combinePage.getSize(),
                combinePage.getNumber(),
                combinePage.isEmpty());
    }

    public void update(Integer id, Integer userId, CombineUpdateRequest request) {
        final Optional<Combine> optionalCombine = combineRepository.findByIdAndUserId(id, userId);
        if (optionalCombine.isEmpty()) {
            throw new ApiException(ErrorEnum.COMBINE_NOT_FOUND);
        }
        final Combine combine = optionalCombine.get();
        combine.setName(request.getName());
        combineRepository.save(combine);
    }

    public CombineDto getCombineById(Integer id, Integer userId) {
        return combineRepository.findByIdAndUserId(id, userId)
                .map(combineConverter::convertAsDto)
                .orElseThrow(() -> new ApiException(ErrorEnum.BOTTOM_NOT_FOUND));
    }

    @Transactional
    public void delete(Integer combineId, Integer userId) {
        final Optional<Combine> optionalCombine = combineRepository.findByIdAndUserId(combineId, userId);
        if (optionalCombine.isEmpty()) {
            throw new ApiException(ErrorEnum.COMBINE_NOT_FOUND);
        }
        final Combine combine = optionalCombine.get();
        combineRepository.delete(combine);
    }

    public void deleteByUserIdAndTopId(Integer userId, Integer topId) {
        combineRepository.deleteByUserIdAndTopId(userId, topId);
    }

    public void deleteByUserIdAndBottomId(Integer userId, Integer bottomId) {
        combineRepository.deleteByUserIdAndBottomId(userId, bottomId);
    }

    public void deleteByUserIdAndShoesId(Integer userId, Integer shoesId) {
        combineRepository.deleteByUserIdAndShoesId(userId, shoesId);
    }
}