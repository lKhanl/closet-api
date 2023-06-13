package dev.oguzhanercelik.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombineUpdateRequest {

    @NotBlank(message = "combineUpdateRequest.name.notBlank")
    private String name;

}