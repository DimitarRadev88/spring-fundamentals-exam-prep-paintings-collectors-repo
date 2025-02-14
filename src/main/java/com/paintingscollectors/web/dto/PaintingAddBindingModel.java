package com.paintingscollectors.web.dto;

import com.paintingscollectors.painting.model.Style;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaintingAddBindingModel {

    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!")
    private String name;
    @Size(min = 5, max = 30, message = "Author name must be between 5 and 30 characters!")
    private String author;
    @Pattern(regexp = "^http(s)://.+\\.(jpeg|png|jpg)$", message = "Please enter valid URL!")
    private String imageUrl;
    @NotNull(message = "You must select a style!")
    private Style style;

}
