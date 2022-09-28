package com.benitomiyazato.ecommercelivros.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "gender")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Gender {

    @Id
    @SequenceGenerator(name = "gender_sequence", sequenceName = "gender_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gender_sequence")
    private Long genderId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @ManyToMany(mappedBy = "genders", fetch = FetchType.LAZY)
    private List<Book> books;
}
