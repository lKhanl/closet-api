package dev.oguzhanercelik.model.request;

import dev.oguzhanercelik.model.BaseFilterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BottomFilterRequest extends BaseFilterRequest {

    private String name;

}
