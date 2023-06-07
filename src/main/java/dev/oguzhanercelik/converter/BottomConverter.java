package dev.oguzhanercelik.converter;

import dev.oguzhanercelik.entity.Bottom;
import dev.oguzhanercelik.model.dto.BottomDto;
import dev.oguzhanercelik.model.request.BottomCreateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BottomConverter {

    @Value("${minio.url}")
    private String baseUrl;

    public Bottom convertAsEntity(Integer userId, BottomCreateRequest request) {
        return Bottom.builder()
                .name(request.getName())
                .userId(userId)
                .build();
    }

    public BottomDto convertAsDto(Bottom bottom) {
        return BottomDto.builder()
                .id(bottom.getId())
                .name(bottom.getName())
                .url(Objects.isNull(bottom.getPath()) ? null : baseUrl + bottom.getPath())
                .build();
    }

}
