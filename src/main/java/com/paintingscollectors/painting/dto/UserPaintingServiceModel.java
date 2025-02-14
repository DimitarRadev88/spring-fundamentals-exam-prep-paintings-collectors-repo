package com.paintingscollectors.painting.dto;

import com.paintingscollectors.painting.model.Style;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPaintingServiceModel {

    private UUID id;
    private String name;
    private String author;
    private Style style;
    private String imageUrl;

}
