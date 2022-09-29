package com.benitomiyazato.ecommercelivros.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class AuthorDto {

    private Long authorId;

    @NotBlank(message = "Nome não pode ser em branco ou nulo.")
    private String name;

    @NotBlank(message = "Biografia não pode ser em branco ou nulo.")
    @Size(max = 601, message = "Biografia não pode ser maior do que 600 caracteres!")
    private String biography;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthdate;
}
