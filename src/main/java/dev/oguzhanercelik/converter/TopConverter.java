package dev.oguzhanercelik.converter;

import dev.oguzhanercelik.entity.Top;
import dev.oguzhanercelik.model.dto.TopDto;
import dev.oguzhanercelik.model.request.TopCreateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TopConverter {

    @Value("${minio.public.url}")
    private String baseUrl;

    public Top convertAsEntity(Integer userId, TopCreateRequest request) {
        return Top.builder()
                .name(request.getName())
                .userId(userId)
                .build();
    }

    public TopDto convertAsDto(Top top) {
        return TopDto.builder()
                .id(top.getId())
                .name(top.getName())
                .url(Objects.isNull(top.getPath()) ? null : baseUrl + top.getPath())
                .build();
    }

}
