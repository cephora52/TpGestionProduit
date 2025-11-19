package com.example.tpgestionproduit.requests;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO pour la création d'un nouveau produit.
 * Contient les validations nécessaires pour les données entrantes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    private BigDecimal prix;

    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantiteStock = 0;

    @Size(max = 50, message = "La catégorie ne peut pas dépasser 50 caractères")
    private String categorie;

    private Boolean actif = true;
}