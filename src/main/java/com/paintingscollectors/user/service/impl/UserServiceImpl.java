package com.paintingscollectors.user.service.impl;

import com.paintingscollectors.painting.dto.FavouritePaintingServiceViewModel;
import com.paintingscollectors.painting.dto.UserPaintingServiceModel;
import com.paintingscollectors.user.dto.UserViewServiceModel;
import com.paintingscollectors.user.model.User;
import com.paintingscollectors.user.repository.UserRepository;
import com.paintingscollectors.user.service.UserService;
import com.paintingscollectors.web.dto.UserLoginBindingModel;
import com.paintingscollectors.web.dto.UserRegisterBindingModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserLoginBindingModel doRegister(UserRegisterBindingModel user) {

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match!");
        }

        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getPassword())) {
            throw new RuntimeException("Username or email address already in use!");
        }

        User userEntity = User.builder().username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .build();

        User save = userRepository.save(userEntity);

        UserLoginBindingModel userLogin = new UserLoginBindingModel();
        userLogin.setUsername(save.getUsername());

        return userLogin;
    }

    @Override
    public UUID doLogin(UserLoginBindingModel userLogin) {
        Optional<User> optionalUser = userRepository.findUserByUsername(userLogin.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Incorrect username or password!");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect username or password!");
        }

        return user.getId();
    }

    @Override
    @Transactional
    public UserViewServiceModel getViewById(UUID userId) {


        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        UserViewServiceModel userView = UserViewServiceModel.builder()
                .username(user.getUsername())
                .paintingList(user.getPaintings().stream().map(p -> UserPaintingServiceModel.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .author(p.getAuthor())
                                .style(p.getStyle())
                                .imageUrl(p.getImageUrl())
                                .build())
                        .toList())
                .favouritePaintings(user.getFavouritePaintings().stream().map(p -> FavouritePaintingServiceViewModel.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .author(p.getAuthor())
                                .imageUrl(p.getImageUrl())
                                .madeFavouriteAt(LocalDateTime.now())
                                .build())
                        .toList())
                .build();
        return userView;
    }

    @Override
    public User getById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
