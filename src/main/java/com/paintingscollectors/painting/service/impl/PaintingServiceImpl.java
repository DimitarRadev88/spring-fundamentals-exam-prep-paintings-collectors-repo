package com.paintingscollectors.painting.service.impl;

import com.paintingscollectors.painting.dto.PaintingsVotesServiceModel;
import com.paintingscollectors.painting.dto.OtherPaintingServiceModel;
import com.paintingscollectors.painting.model.FavouritePainting;
import com.paintingscollectors.painting.model.Painting;
import com.paintingscollectors.painting.repository.FavouritePaintingRepository;
import com.paintingscollectors.painting.repository.PaintingRepository;
import com.paintingscollectors.painting.service.PaintingService;
import com.paintingscollectors.user.service.UserService;
import com.paintingscollectors.web.dto.PaintingAddBindingModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaintingServiceImpl implements PaintingService {

    private final PaintingRepository paintingRepository;
    private final FavouritePaintingRepository favouritePaintingRepository;
    private final UserService userService;

    @Autowired
    public PaintingServiceImpl(PaintingRepository paintingRepository, FavouritePaintingRepository favouritePaintingRepository, UserService userService) {
        this.paintingRepository = paintingRepository;
        this.favouritePaintingRepository = favouritePaintingRepository;
        this.userService = userService;
    }

    @Override
    public void doAdd(PaintingAddBindingModel paintingAddBindingModel, UUID userId) {
        Painting painting = Painting.builder()
                .name(paintingAddBindingModel.getName())
                .author(paintingAddBindingModel.getAuthor())
                .imageUrl(paintingAddBindingModel.getImageUrl())
                .style(paintingAddBindingModel.getStyle())
                .owner(userService.getById(userId))
                .votes(0)
                .build();

        paintingRepository.save(painting);
    }

    @Override
    public void doDelete(UUID id) {
        paintingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<OtherPaintingServiceModel> getAllExceptUserOwned(UUID userId) {
        List<Painting> allOther = paintingRepository.findAllByOwnerIdNot(userId);

        return allOther
                .stream()
                .map(p -> OtherPaintingServiceModel.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .author(p.getAuthor())
                        .style(p.getStyle())
                        .imageUrl(p.getImageUrl())
                        .addedBy(p.getOwner().getUsername())
                        .build()
                ).toList();
    }

    @Override
    public void addFavouriteById(UUID id, UUID userId) {
        Optional<Painting> byId = paintingRepository.findById(id);

        if (byId.isEmpty()) {
            throw new RuntimeException("Painting not found");
        }

        FavouritePainting favouritePainting = FavouritePainting.builder()
                .name(byId.get().getName())
                .author(byId.get().getAuthor())
                .imageUrl(byId.get().getImageUrl())
                .owner(userService.getById(userId))
                .madeFavouriteAt(LocalDateTime.now())
                .build();

        favouritePaintingRepository.save(favouritePainting);
    }

    @Override
    public void doDeleteFavourite(UUID id) {
        favouritePaintingRepository.deleteById(id);
    }

    @Override
    public void addVotes(UUID id) {
        Painting painting = paintingRepository.findById(id).orElseThrow(() -> new RuntimeException("Painting not found"));
        painting.setVotes(painting.getVotes() + 1);
        paintingRepository.save(painting);
    }

    @Override
    public List<PaintingsVotesServiceModel> getAllPaintingsOrderedByVotesAndName() {
        return paintingRepository.findAllByOrderByVotesDescNameAsc()
                .stream()
                .map(p -> PaintingsVotesServiceModel.builder()
                        .name(p.getName())
                        .votes(p.getVotes())
                        .build()).toList();
    }

}
