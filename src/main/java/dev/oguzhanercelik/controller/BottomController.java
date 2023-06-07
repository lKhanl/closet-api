package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.BottomDto;
import dev.oguzhanercelik.model.request.BottomCreateRequest;
import dev.oguzhanercelik.model.request.BottomFilterRequest;
import dev.oguzhanercelik.service.BottomService;
import dev.oguzhanercelik.utils.IdentityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
