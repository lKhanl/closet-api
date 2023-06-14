package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.TopConverter;
import dev.oguzhanercelik.entity.Top;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.dto.TopDto;
import dev.oguzhanercelik.model.enums.Path;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.TopCreateRequest;
import dev.oguzhanercelik.model.request.TopUpdateRequest;
import dev.oguzhanercelik.repository.TopRepository;
import lombok.RequiredArgsConstructor;
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
    private final CombineService combineService;

    public void create(Integer userId, TopCreateRequest request) {
        final Top top = topConverter.convertAsEntity(userId, request);
        topRepository.save(top);
    }

    public List<TopDto> getAllTop(Integer userId) {
        final List<Top> tops = topRepository.findByUserIdOrderByIdDesc(userId);
        return tops.stream()
                .map(topConverter::convertAsDto)
                .toList();
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

    public TopDto getTopById(Integer id, Integer userId) {
        return topRepository.findByIdAndUserId(id, userId)
                .map(topConverter::convertAsDto)
                .orElseThrow(() -> new ApiException(ErrorEnum.TOP_NOT_FOUND));
    }

    @Transactional
    public void delete(Integer topId, Integer userId) {
        final Optional<Top> optionalTop = topRepository.findByIdAndUserId(topId, userId);
        if (optionalTop.isEmpty()) {
            throw new ApiException(ErrorEnum.TOP_NOT_FOUND);
        }
        final Top top = optionalTop.get();

        if (top.getPath() != null) {
            storageService.deleteFile(top.getPath());
        }
        combineService.deleteByUserIdAndTopId(userId, topId);
        topRepository.delete(top);
    }
}
