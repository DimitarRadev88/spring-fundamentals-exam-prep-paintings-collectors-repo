package com.paintingscollectors.painting.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavouritePaintingServiceViewModel {

    private UUID id;
    private String name;
    private String author;
    private String imageUrl;
    private LocalDateTime madeFavouriteAt;

}
