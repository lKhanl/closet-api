package dev.oguzhanercelik.controller;

import dev.oguzhanercelik.model.PagingResult;
import dev.oguzhanercelik.model.dto.CombineDto;
import dev.oguzhanercelik.model.request.CombineCreateRequest;
import dev.oguzhanercelik.model.request.CombineFilterRequest;
import dev.oguzhanercelik.model.request.CombineUpdateRequest;
import dev.oguzhanercelik.service.CombineService;
import dev.oguzhanercelik.utils.IdentityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/combines")
@RequiredArgsConstructor
public class CombineController {

    private final CombineService combineService;

    @PostMapping
    public void createTop(@RequestBody @Valid CombineCreateRequest request) {
        combineService.create(IdentityUtils.getId(), request);
    }

    @GetMapping
    public PagingResult<CombineDto> getAllByFilterCombine(@Valid CombineFilterRequest request) {
        return combineService.getAllByFilterCombine(IdentityUtils.getId(), request);
    }

//    @GetMapping("/{id}")
//    public TopDto getTopById(@PathVariable Integer id) {
//        return combineService.getTopById(id, IdentityUtils.getId());
//    }

    @PutMapping("/{combineId}")
    public void updateCombine(@RequestBody @Valid CombineUpdateRequest request, @PathVariable Integer combineId) {
        combineService.update(combineId, IdentityUtils.getId(), request);
    }

    @DeleteMapping("/{combineId}")
    public void delete(@PathVariable Integer combineId) {
        combineService.delete(combineId, IdentityUtils.getId());
    }
}