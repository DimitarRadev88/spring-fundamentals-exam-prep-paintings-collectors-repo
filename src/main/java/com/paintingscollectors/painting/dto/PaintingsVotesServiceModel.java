package com.paintingscollectors.painting.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaintingsVotesServiceModel {

    private String name;
    private Integer votes;

}
