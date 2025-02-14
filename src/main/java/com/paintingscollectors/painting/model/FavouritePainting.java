package com.paintingscollectors.painting.model;

import com.paintingscollectors.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "favourite_paintings")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavouritePainting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @ManyToOne
    private User owner;
    @Column(nullable = false)
    private String imageUrl;
    @Basic
    private LocalDateTime madeFavouriteAt;

}
