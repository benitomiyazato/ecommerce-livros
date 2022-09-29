package com.benitomiyazato.ecommercelivros.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class AuthorDto {

    @NotBlank(message = "Nome não pode ser em branco ou nulo!")
    private String name;

    @NotBlank(message = "Biografia não pode ser em branco ou nulo!")
    private String biography;
}
