package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.TopDto;
import dev.oguzhanercelik.model.request.TopCreateRequest;
import dev.oguzhanercelik.model.request.TopFilterRequest;
import dev.oguzhanercelik.model.request.TopUpdateRequest;
import dev.oguzhanercelik.service.TopService;
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
@RequestMapping("/api/v1/tops")
@RequiredArgsConstructor
public class TopController {

    private final TopService topService;

    @PostMapping
    public void createTop(@RequestBody @Valid TopCreateRequest request) {
        topService.create(IdentityUtils.getId(), request);
    }

    @GetMapping
    public PagingResult<TopDto> getAllByFilterTop(@Valid TopFilterRequest request) {
        return topService.getAllByFilterTop(IdentityUtils.getId(), request);
    }

    @GetMapping("/{id}")
    public TopDto getTopById(@PathVariable Integer id) {
        return topService.getTopById(id, IdentityUtils.getId());
    }

    @PutMapping("/{id}")
    public void updateTop(@RequestBody @Valid TopUpdateRequest request, @PathVariable Integer id) {
        topService.update(id, IdentityUtils.getId(), request);
    }

    @PostMapping(value = "/{topId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestPart MultipartFile file, @PathVariable Integer topId) {
        topService.uploadImage(topId, IdentityUtils.getId(), file);
    }

    @DeleteMapping(value = "/{topId}/image")
    public void deleteImage(@PathVariable Integer topId) {
        topService.deleteImage(topId, IdentityUtils.getId());
    }

}
