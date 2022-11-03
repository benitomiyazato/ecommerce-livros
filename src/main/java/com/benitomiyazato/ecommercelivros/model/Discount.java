package com.benitomiyazato.ecommercelivros.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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
    private LocalDateTime expirationDate;

    @OneToOne(mappedBy = "discount")
    private Book book;
}
