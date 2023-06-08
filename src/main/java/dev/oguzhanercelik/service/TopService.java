package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.TopConverter;
import dev.oguzhanercelik.entity.Top;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.TopDto;
import dev.oguzhanercelik.model.enums.Path;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.TopCreateRequest;
import dev.oguzhanercelik.model.request.TopFilterRequest;
import dev.oguzhanercelik.model.request.TopUpdateRequest;
import dev.oguzhanercelik.repository.TopRepository;
import dev.oguzhanercelik.repository.TopSpecification;
import dev.oguzhanercelik.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopService {

    private final TopRepository topRepository;
    private final TopConverter topConverter;
    private final StorageService storageService;

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

    @Transactional
    public void uploadImage(Integer topId, Integer userId, MultipartFile file) {
        final Optional<Top> optionalTop = topRepository.findByIdAndUserId(topId, userId);
        if (optionalTop.isEmpty()) {
            throw new ApiException(ErrorEnum.TOP_NOT_FOUND);
        }
        final Top top = optionalTop.get();

        if (Objects.nonNull(top.getPath())) {
            storageService.deleteFile(top.getPath());
        }
        final String path = storageService.uploadFile(Path.TOP, file);
        top.setPath(path);
        topRepository.save(top);
    }

    @Transactional
    public void deleteImage(Integer topId, Integer userId) {
        final Optional<Top> optionalTop = topRepository.findByIdAndUserId(topId, userId);
        if (optionalTop.isEmpty()) {
            throw new ApiException(ErrorEnum.TOP_NOT_FOUND);
        }
        final Top top = optionalTop.get();

        storageService.deleteFile(top.getPath());
        top.setPath(null);
        topRepository.save(top);
    }
}
