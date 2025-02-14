package com.paintingscollectors.web;

import com.paintingscollectors.web.dto.UserLoginBindingModel;
import com.paintingscollectors.user.service.UserService;
import com.paintingscollectors.web.dto.UserRegisterBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        if (!model.containsAttribute("userRegister")) {
            model.addAttribute("userRegister", new UserRegisterBindingModel());
        }

        return "register";
    }

    @PostMapping("/register")
    public ModelAndView postRegister(ModelAndView modelAndView,
                                     @Valid @ModelAttribute("userRegister") UserRegisterBindingModel userRegister,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegister", bindingResult);
            redirectAttributes.addFlashAttribute("userRegister", userRegister);
            modelAndView.setViewName("redirect:/register");
        } else {
            UserLoginBindingModel userLogin = userService.doRegister(userRegister);
            modelAndView.setViewName("redirect:/login");
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        if (!model.containsAttribute("userLogin")) {
            model.addAttribute("userLogin", new UserLoginBindingModel());
        }

        return "login";
    }

    @PostMapping("/login")
    public ModelAndView postLogin(ModelAndView modelAndView,
                                  @Valid @ModelAttribute("userLogin") UserLoginBindingModel userLogin,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLogin", bindingResult);
            redirectAttributes.addFlashAttribute("userLogin", userLogin);
            modelAndView.setViewName("redirect:/login");
        } else {
            UUID id = userService.doLogin(userLogin);
            httpSession.setAttribute("user_id", id);
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

}
