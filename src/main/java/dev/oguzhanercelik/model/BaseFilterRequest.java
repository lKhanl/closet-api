package dev.oguzhanercelik.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseFilterRequest {

    @Builder.Default
    @Min(value = 1, message = "baseFilterRequest.page.min")
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    @Builder.Default
    private String sortField = "id";

    @Builder.Default
    private Sort.Direction direction = Sort.Direction.DESC;
}