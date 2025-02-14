package com.paintingscollectors.user.service;

import com.paintingscollectors.user.dto.UserViewServiceModel;
import com.paintingscollectors.user.model.User;
import com.paintingscollectors.web.dto.UserLoginBindingModel;
import com.paintingscollectors.web.dto.UserRegisterBindingModel;

import java.util.UUID;

public interface UserService {
    UserLoginBindingModel doRegister(UserRegisterBindingModel user);

    UUID doLogin(UserLoginBindingModel userLogin);

    UserViewServiceModel getViewById(UUID userId);

    User getById(UUID userId);
}
