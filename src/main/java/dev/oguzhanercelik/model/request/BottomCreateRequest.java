package dev.oguzhanercelik.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BottomCreateRequest {

    @NotBlank(message = "bottomCreateRequest.name.notBlank")
    private String name;

}
