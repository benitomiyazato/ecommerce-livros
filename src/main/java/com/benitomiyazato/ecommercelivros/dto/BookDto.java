package com.benitomiyazato.ecommercelivros.dto;

import com.benitomiyazato.ecommercelivros.model.Author;
import com.benitomiyazato.ecommercelivros.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private Long bookId;

    @NotBlank(message = "Não pode ser em branco ou nulo.")
    private String title;

    @NotNull(message = "Insira um preço")
    @Min(value = 1, message = "O preço deve ser maior que zero.")
    private double price;

    @NotBlank(message = "Descrição não pode ser em branco ou nulo.")
    @Size(max = 601, message = "Descrição não pode ser maior do que 600 caracteres!")
    private String description;

    private int quantityInStock;

    @NotNull(message = "Este livro precisa de um autor.")
    private Long authorId;

    @NotNull(message = "Este livro precisa de ao menos um gênero.")
    private List<Long> genderIds;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

}
