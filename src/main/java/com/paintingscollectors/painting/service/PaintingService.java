package com.paintingscollectors.painting.service;

import com.paintingscollectors.painting.dto.PaintingsVotesServiceModel;
import com.paintingscollectors.painting.dto.OtherPaintingServiceModel;
import com.paintingscollectors.web.dto.PaintingAddBindingModel;

import java.util.List;
import java.util.UUID;

public interface PaintingService {
    void doAdd(PaintingAddBindingModel paintingAddBindingModel, UUID userId);

    void doDelete(UUID id);

    List<OtherPaintingServiceModel> getAllExceptUserOwned(UUID userId);

    void addFavouriteById(UUID id, UUID userId);

    void doDeleteFavourite(UUID id);

    void addVotes(UUID id);

    List<PaintingsVotesServiceModel> getAllPaintingsOrderedByVotesAndName();
}
