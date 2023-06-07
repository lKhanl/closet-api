package dev.oguzhanercelik.converter;

import dev.oguzhanercelik.model.IdentityUser;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class IdentityUserConverter {

    public IdentityUser getUser(Claims claims) {
        return IdentityUser.builder()
                .id(Integer.valueOf(Objects.requireNonNull(getStringValue(claims, "id"))))
                .email(getStringValue(claims, "email"))
                .build();
    }

    private String getStringValue(Claims claims, String key) {
        final Object foundValue = claims.getOrDefault(key, StringUtils.EMPTY);
        return foundValue == null ? null : foundValue.toString();
    }
}