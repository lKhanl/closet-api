package dev.oguzhanercelik.converter;

import dev.oguzhanercelik.entity.Shoes;
import dev.oguzhanercelik.model.dto.ShoesDto;
import dev.oguzhanercelik.model.request.ShoesCreateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ShoesConverter {

    @Value("${minio.public.url}")
    private String baseUrl;

    public Shoes convertAsEntity(Integer userId, ShoesCreateRequest request) {
        return Shoes.builder()
                .name(request.getName())
                .userId(userId)
                .build();
    }

    public ShoesDto convertAsDto(Shoes shoes) {
        return ShoesDto.builder()
                .id(shoes.getId())
                .name(shoes.getName())
                .url(Objects.isNull(shoes.getPath()) ? null : baseUrl + shoes.getPath())
                .build();
    }

}
