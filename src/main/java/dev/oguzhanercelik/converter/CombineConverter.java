package dev.oguzhanercelik.converter;

import dev.oguzhanercelik.entity.Combine;
import dev.oguzhanercelik.model.dto.CombineDto;
import dev.oguzhanercelik.model.request.CombineCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CombineConverter {

    private final TopConverter topConverter;
    private final BottomConverter bottomConverter;
    private final ShoesConverter shoesConverter;

    public Combine convertAsEntity(Integer userId, CombineCreateRequest request) {
        return Combine.builder()
                .name(request.getName())
                .topId(request.getTopId())
                .bottomId(request.getBottomId())
                .shoesId(request.getShoesId())
                .userId(userId)
                .build();
    }

    public CombineDto convertAsDto(Combine combine) {
        return CombineDto.builder()
                .id(combine.getId())
                .name(combine.getName())
                .top(topConverter.convertAsDto(combine.getTop()))
                .bottom(bottomConverter.convertAsDto(combine.getBottom()))
                .shoes(shoesConverter.convertAsDto(combine.getShoes()))
                .build();
    }

}