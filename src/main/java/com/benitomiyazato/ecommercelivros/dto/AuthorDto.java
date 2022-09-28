package com.benitomiyazato.ecommercelivros.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AuthorDto {

    @NotBlank(message = "Nome não pode ser em branco ou nulo!")
    private String name;

    @Min(value = 150, message = "Deve ter ao menos 150 caracteres!")
    @Min(value = 500, message = "Deve ter no máximo 500 caracteres!")
    private String biography;
}
