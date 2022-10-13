package com.benitomiyazato.ecommercelivros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CollectionDto {
    private Long collectionId;

    @NotBlank(message = "Não pode ser em branco ou nulo.")
    private String title;

    @NotNull(message = "Insira um preço")
    @Min(value = 1, message = "O preço deve ser maior que zero.")
    private double price;

    @NotBlank(message = "Descrição não pode ser em branco ou nulo.")
    @Size(max = 601, message = "Descrição não pode ser maior do que 600 caracteres!")
    private String description;

    @NotNull(message = "Insira ao menos uma imagem.")
    private MultipartFile image1;

    private MultipartFile image2;
    private MultipartFile image3;

    @NotNull(message = "Este livro precisa de ao menos um gênero.")
    private List<Long> bookIds;

    private boolean editing;
}
