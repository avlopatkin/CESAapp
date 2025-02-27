package com.currencyexchange.currencyexchangespringapplication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "currencies", uniqueConstraints = {
        @UniqueConstraint(columnNames = "code")
})
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "sign", nullable = false)
    private String sign;

    public Currency(String code, String fullName, String sign) {
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }
}
