package com.example.tpgestionproduit.repositories;

import com.eadl.gestion_produit.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Interface Repository pour gérer les opérations CRUD sur les produits.
 * Hérite de JpaRepository qui fournit les méthodes CRUD de base.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Recherche les produits par catégorie.
     * @param categorie La catégorie recherchée
     * @return Liste des produits de cette catégorie
     */
    List<Product> findByCategorie(String categorie);

    /**
     * Recherche les produits actifs uniquement.
     * @param actif Statut d'activité (true pour actifs)
     * @return Liste des produits actifs
     */
    List<Product> findByActif(Boolean actif);

    /**
     * Recherche les produits dont le nom contient le texte spécifié (insensible à la casse).
     * @param nom Partie du nom à rechercher
     * @return Liste des produits correspondants
     */
    List<Product> findByNomContainingIgnoreCase(String nom);

    /**
     * Recherche les produits avec une quantité en stock inférieure à un seuil.
     * @param seuil Quantité minimale
     * @return Liste des produits en rupture de stock
     */
    @Query("SELECT p FROM Product p WHERE p.quantiteStock < :seuil")
    List<Product> findProductsInStockBelow(@Param("seuil") Integer seuil);
}