package com.benitomiyazato.ecommercelivros.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class GenderDto {

    private Long genderId;

    @NotBlank(message = "Nome não pode ser em branco ou nulo.")
    private String name;

    @NotBlank(message = "Descrição não pode ser em branco ou nulo.")
    @Size(max = 601, message = "Descrição não pode ser maior do que 600 caracteres!")
    private String description;

    private boolean editing;

}
