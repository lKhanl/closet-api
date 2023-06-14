package dev.oguzhanercelik.service;

import dev.oguzhanercelik.converter.ShoesConverter;
import dev.oguzhanercelik.entity.Shoes;
import dev.oguzhanercelik.exception.ApiException;
import dev.oguzhanercelik.model.dto.ShoesDto;
import dev.oguzhanercelik.model.enums.Path;
import dev.oguzhanercelik.model.error.ErrorEnum;
import dev.oguzhanercelik.model.request.ShoesCreateRequest;
import dev.oguzhanercelik.model.request.ShoesUpdateRequest;
import dev.oguzhanercelik.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoesService {

    private final ShoesRepository shoesRepository;
    private final ShoesConverter shoesConverter;
    private final StorageService storageService;
    private final CombineService combineService;

    public void create(Integer userId, ShoesCreateRequest request) {
        final Shoes shoes = shoesConverter.convertAsEntity(userId, request);
        shoesRepository.save(shoes);
    }

    public List<ShoesDto> getAllShoes(Integer id) {
        final List<Shoes> shoes = shoesRepository.findByUserIdOrderByIdDesc(id);
        return shoes.stream()
                .map(shoesConverter::convertAsDto)
                .toList();
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

    @Transactional
    public void uploadImage(Integer shoesId, Integer userId, MultipartFile file) {
        final Optional<Shoes> optionalShoes = shoesRepository.findByIdAndUserId(shoesId, userId);
        if (optionalShoes.isEmpty()) {
            throw new ApiException(ErrorEnum.SHOES_NOT_FOUND);
        }
        final Shoes shoes = optionalShoes.get();

        if (Objects.nonNull(shoes.getPath())) {
            storageService.deleteFile(shoes.getPath());
        }
        final String path = storageService.uploadFile(Path.SHOES, file);
        shoes.setPath(path);
        shoesRepository.save(shoes);
    }

    @Transactional
    public void deleteImage(Integer shoesId, Integer userId) {
        final Optional<Shoes> optionalShoes = shoesRepository.findByIdAndUserId(shoesId, userId);
        if (optionalShoes.isEmpty()) {
            throw new ApiException(ErrorEnum.SHOES_NOT_FOUND);
        }
        final Shoes shoes = optionalShoes.get();

        storageService.deleteFile(shoes.getPath());
        shoes.setPath(null);
        shoesRepository.save(shoes);
    }

    public ShoesDto getShoesById(Integer id, Integer userId) {
        return shoesRepository.findByIdAndUserId(id, userId)
                .map(shoesConverter::convertAsDto)
                .orElseThrow(() -> new ApiException(ErrorEnum.SHOES_NOT_FOUND));
    }

    @Transactional
    public void delete(Integer shoesId, Integer userId) {
        final Optional<Shoes> optionalShoes = shoesRepository.findByIdAndUserId(shoesId, userId);
        if (optionalShoes.isEmpty()) {
            throw new ApiException(ErrorEnum.SHOES_NOT_FOUND);
        }
        final Shoes shoes = optionalShoes.get();

        if (shoes.getPath() != null) {
            storageService.deleteFile(shoes.getPath());
        }
        combineService.deleteByUserIdAndShoesId(userId, shoesId);
        shoesRepository.delete(shoes);
    }
}
