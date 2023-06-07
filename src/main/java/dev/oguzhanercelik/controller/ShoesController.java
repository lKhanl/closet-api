package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.ShoesDto;
import dev.oguzhanercelik.model.request.ShoesCreateRequest;
import dev.oguzhanercelik.model.request.ShoesFilterRequest;
import dev.oguzhanercelik.service.ShoesService;
import dev.oguzhanercelik.utils.IdentityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
