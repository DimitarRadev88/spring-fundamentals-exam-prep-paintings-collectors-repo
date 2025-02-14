package com.paintingscollectors.user.dto;

import com.paintingscollectors.painting.dto.FavouritePaintingServiceViewModel;
import com.paintingscollectors.painting.dto.UserPaintingServiceModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserViewServiceModel {

    private String username;
    private List<UserPaintingServiceModel> paintingList;
    private List<FavouritePaintingServiceViewModel> favouritePaintings;

}
