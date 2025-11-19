package com.eadl.gestion_produit.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité représentant un produit dans la base de données.
 * Cette classe est mappée à la table "produits" via JPA.
 */
@Entity
@Table(name = "produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Identifiant unique du produit.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du produit.
     * Ne peut pas être null ou vide, maximum 100 caractères.
     */
    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    @Column(nullable = false, length = 100)
    private String nom;

    /**
     * Description détaillée du produit.
     * Maximum 500 caractères.
     */
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    @Column(length = 350)
    private String description;

    /**
     * Prix du produit en euros.
     * Doit être positif et non null.
     */
    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;

    /**
     * Quantité en stock.
     * Doit être supérieur ou égal à 0.
     */
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    @Column(nullable = false)
    private Integer quantiteStock = 0;

    /**
     * Catégorie du produit (ex: Électronique, Vêtements, Alimentation).
     */
    @Size(max = 50, message = "La catégorie ne peut pas dépasser 50 caractères")
    @Column(length = 50)
    private String categorie;

    /**
     * Indique si le produit est actif/disponible à la vente.
     */
    @Column(nullable = false)
    private Boolean actif = true;

    /**
     * Date de création du produit.
     * Renseignée automatiquement lors de la création.
     */
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    /**
     * Date de dernière modification.
     * Mise à jour automatiquement lors de chaque modification.
     */
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    /**
     * Méthode appelée automatiquement avant la persistance de l'entité.
     */
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    /**
     * Méthode appelée automatiquement avant chaque mise à jour.
     */
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}