package com.eadl.gestion_produit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object pour les réponses API.
 * Permet de contrôler exactement quelles données sont exposées au frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private Integer quantiteStock;
    private String categorie;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}