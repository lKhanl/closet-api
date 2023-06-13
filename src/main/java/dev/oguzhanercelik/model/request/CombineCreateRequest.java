package dev.oguzhanercelik.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombineCreateRequest {

    @NotNull(message = "combineCreateRequest.topId.notNull")
    private Integer topId;

    @NotNull(message = "combineCreateRequest.bottomId.notNull")
    private Integer bottomId;

    @NotNull(message = "combineCreateRequest.shoesId.notNull")
    private Integer shoesId;

    @NotBlank(message = "combineCreateRequest.name.notBlank")
    private String name;

}