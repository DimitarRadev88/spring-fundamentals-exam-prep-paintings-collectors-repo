package com.paintingscollectors.web;

import com.paintingscollectors.painting.dto.PaintingsVotesServiceModel;
import com.paintingscollectors.painting.dto.OtherPaintingServiceModel;
import com.paintingscollectors.painting.service.PaintingService;
import com.paintingscollectors.user.dto.UserViewServiceModel;
import com.paintingscollectors.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    private final UserService userService;
    private final PaintingService paintingService;

    public IndexController(UserService userService, PaintingService paintingService) {
        this.userService = userService;
        this.paintingService = paintingService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model, HttpSession session) {
        if (session.getAttribute("user_id") == null) {
            return "index";
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        UserViewServiceModel user = userService.getViewById(userId);
        model.addAttribute("user", user);

        List<OtherPaintingServiceModel> allOtherPaintings = paintingService.getAllExceptUserOwned(userId);
        model.addAttribute("allOtherPaintings", allOtherPaintings);

        List<PaintingsVotesServiceModel> allPaintingsVotes = paintingService.getAllPaintingsOrderedByVotesAndName();
        model.addAttribute("allPaintingsVotes", allPaintingsVotes);

        return "home";
    }

}
