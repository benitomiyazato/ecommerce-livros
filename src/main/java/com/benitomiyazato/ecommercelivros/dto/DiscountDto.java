package com.benitomiyazato.ecommercelivros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DiscountDto {
    private Long discountId;

    @Max(value = 100)
    private double percentageOfDiscount;

    @NotNull(message = "Este desconto deve ser associado a um livro.")
    private Long bookId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    private LocalTime expirationTime;

    private boolean editing;
}
