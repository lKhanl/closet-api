package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.ShoesDto;
import dev.oguzhanercelik.model.request.ShoesCreateRequest;
import dev.oguzhanercelik.model.request.ShoesFilterRequest;
import dev.oguzhanercelik.model.request.ShoesUpdateRequest;
import dev.oguzhanercelik.service.ShoesService;
import dev.oguzhanercelik.utils.IdentityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/shoes")
@RequiredArgsConstructor
public class ShoesController {

    private final ShoesService shoesService;

    @PostMapping
    public void createShoes(@RequestBody ShoesCreateRequest request) {
        shoesService.create(IdentityUtils.getId(), request);
    }

    @GetMapping
    public PagingResult<ShoesDto> getAllByFilterShoes(@Valid ShoesFilterRequest request) {
        return shoesService.getAllByFilterShoes(IdentityUtils.getId(), request);
    }

    @PutMapping("/{id}")
    public void updateShoes(@RequestBody @Valid ShoesUpdateRequest request, @PathVariable Integer id) {
        shoesService.update(id, IdentityUtils.getId(), request);
    }


    @PostMapping(value = "/{shoesId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestPart MultipartFile file, @PathVariable Integer shoesId) {
        shoesService.uploadImage(shoesId, IdentityUtils.getId(), file);
    }

    @DeleteMapping(value = "/{shoesId}/image")
    public void deleteImage(@PathVariable Integer shoesId) {
        shoesService.deleteImage(shoesId, IdentityUtils.getId());
    }

}
