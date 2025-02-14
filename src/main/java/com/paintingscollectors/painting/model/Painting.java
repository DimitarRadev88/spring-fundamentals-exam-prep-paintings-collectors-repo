package com.paintingscollectors.painting.model;

import com.paintingscollectors.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "paintings")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Style style;
    @ManyToOne
    private User owner;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private Integer votes;

}
