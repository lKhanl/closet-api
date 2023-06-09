package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.BottomDto;
import dev.oguzhanercelik.model.request.BottomCreateRequest;
import dev.oguzhanercelik.model.request.BottomFilterRequest;
import dev.oguzhanercelik.model.request.BottomUpdateRequest;
import dev.oguzhanercelik.service.BottomService;
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
@RequestMapping("/api/v1/bottoms")
@RequiredArgsConstructor
public class BottomController {

    private final BottomService bottomService;

    @PostMapping
    public void createBottom(@RequestBody BottomCreateRequest request) {
        bottomService.create(IdentityUtils.getId(), request);
    }

    @GetMapping
    public PagingResult<BottomDto> getAllByFilterBottom(@Valid BottomFilterRequest request) {
        return bottomService.getAllByFilterBottom(IdentityUtils.getId(), request);
    }

    @GetMapping("/{id}")
    public BottomDto getBottomById(@PathVariable Integer id) {
        return bottomService.getBottomById(id, IdentityUtils.getId());
    }

    @PutMapping("/{id}")
    public void updateBottom(@RequestBody @Valid BottomUpdateRequest request, @PathVariable Integer id) {
        bottomService.update(id, IdentityUtils.getId(), request);
    }

    @PostMapping(value = "/{bottomId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestPart MultipartFile file, @PathVariable Integer bottomId) {
        bottomService.uploadImage(bottomId, IdentityUtils.getId(), file);
    }

    @DeleteMapping(value = "/{bottomId}/image")
    public void deleteImage(@PathVariable Integer bottomId) {
        bottomService.deleteImage(bottomId, IdentityUtils.getId());
    }
}
