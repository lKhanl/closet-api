package dev.oguzhanercelik.converter;

import dev.oguzhanercelik.entity.User;
import dev.oguzhanercelik.model.dto.UserDto;
import dev.oguzhanercelik.model.request.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User convertAsEntity(RegisterRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public UserDto convertAsDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
