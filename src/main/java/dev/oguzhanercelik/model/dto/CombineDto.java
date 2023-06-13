package dev.oguzhanercelik.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombineDto {

    private Integer id;

    private String name;

    private TopDto top;

    private BottomDto bottom;

    private ShoesDto shoes;

}