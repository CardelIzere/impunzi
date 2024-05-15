package com.refugietransaction.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mvtstkmenage")
public class MvtStkMenage extends AbstractEntity{

    @Column(name = "datemvt")
    @Temporal(TemporalType.TIMESTAMP)
	@Convert(converter = LocalDateConverter.class)
    private LocalDate dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @Column(name = "typemvt")
    @Enumerated(EnumType.STRING)
    private TypeMvtStkMenageEnum typeMvtStkMenageEnum;

    @ManyToOne
    @JoinColumn(name = "menage_id")
    private Menage menage;
}

