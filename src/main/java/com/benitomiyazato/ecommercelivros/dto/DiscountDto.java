package com.benitomiyazato.ecommercelivros.dto;

import com.benitomiyazato.ecommercelivros.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DiscountDto {
    private Long discountId;

    @NotNull(message = "Insira um desconto")
    @Min(value = 1, message = "A quantidade de desconto deve ser maior que zero.")
    private double amountOfDiscount;

    @NotNull(message = "Este desconto deve ser associado a um livro.")
    private Long bookId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    private LocalTime expirationTime;

    private boolean editing;
}
