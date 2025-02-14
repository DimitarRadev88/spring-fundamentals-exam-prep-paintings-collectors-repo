package com.paintingscollectors.web;

import com.paintingscollectors.painting.model.Painting;
import com.paintingscollectors.painting.service.PaintingService;
import com.paintingscollectors.web.dto.PaintingAddBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/paintings")
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping("/add")
    public String getAddPaintingPage(Model model) {
        if (!model.containsAttribute("paintingAddBinding")) {
            model.addAttribute("paintingAddBinding", new PaintingAddBindingModel());
        }

        return "new-painting";
    }

    @PostMapping("/add")
    public ModelAndView postAddPainting(ModelAndView modelAndView,
                                       @ModelAttribute("paintingAddBinding") @Valid PaintingAddBindingModel paintingAddBinding,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes,
                                        HttpSession session) {

         if (bindingResult.hasErrors()) {
             redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paintingAddBinding", bindingResult);
             redirectAttributes.addFlashAttribute("paintingAddBinding", paintingAddBinding);
             modelAndView.setViewName("redirect:/paintings/add");
         } else {
             paintingService.doAdd(paintingAddBinding, (UUID) session.getAttribute("user_id"));
             modelAndView.setViewName("redirect:/");
         }

        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public String deletePainting(@PathVariable UUID id) {
        paintingService.doDelete(id);

        return "redirect:/";
    }

    @PostMapping("/favourites/add/{id}")
    public String getAllPaintings(@PathVariable UUID id, HttpSession session) {
        UUID userId = (UUID) session.getAttribute("user_id");

        paintingService.addFavouriteById(id, userId);

        return "redirect:/";
    }

    @DeleteMapping("/favourites/delete/{id}")
    public String deleteFavourite(@PathVariable UUID id) {
        paintingService.doDeleteFavourite(id);

        return "redirect:/";
    }

    @PatchMapping("/vote/{id}")
    public String vote(@PathVariable UUID id) {
        paintingService.addVotes(id);

        return "redirect:/";
    }

}
