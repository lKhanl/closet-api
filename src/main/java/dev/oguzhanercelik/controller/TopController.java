package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.model.dto.TopDto;
import dev.oguzhanercelik.model.request.TopCreateRequest;
import dev.oguzhanercelik.model.request.TopUpdateRequest;
import dev.oguzhanercelik.service.TopService;
import dev.oguzhanercelik.utils.IdentityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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
    public List<TopDto> getAllTop() {
        return topService.getAllTop(IdentityUtils.getId());
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

    @DeleteMapping("/{topId}/image")
    public void deleteImage(@PathVariable Integer topId) {
        topService.deleteImage(topId, IdentityUtils.getId());
    }

    @DeleteMapping("/{topId}")
    public void delete(@PathVariable Integer topId) {
        topService.delete(topId, IdentityUtils.getId());
    }
}
