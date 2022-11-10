package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "discount")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {
    @Id
    @SequenceGenerator(name = "discount_sequence", sequenceName = "discount_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_sequence")
    private Long discountId;

    @Column(nullable = false)
    private double amountOfDiscount;

    @Column(nullable = false)
    private double percentageOfDiscount;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate expirationDate;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime expirationTime;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;
}
