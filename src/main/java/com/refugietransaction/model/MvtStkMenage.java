package com.refugietransaction.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

