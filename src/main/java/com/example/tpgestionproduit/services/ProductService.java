package com.example.tpgestionproduit.services;
import com.eadl.gestion_produit.dto.ProductDTO;
import com.eadl.gestion_produit.entities.Product;
import com.eadl.gestion_produit.exception.ResourceNotFoundException;
import com.eadl.gestion_produit.repositories.ProductRepository;
import com.eadl.gestion_produit.requests.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service métier pour la gestion des produits.
 * Contient toute la logique métier et fait le lien entre le contrôleur et le repository.
 */
@Service
@RequiredArgsConstructor // Génère un constructeur avec les dépendances final
@Transactional // Toutes les méthodes sont transactionnelles par défaut
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Récupère tous les produits.
     * @return Liste de tous les produits sous forme de DTO
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un produit par son identifiant.
     * @param id Identifiant du produit
     * @return Le produit trouvé sous forme de DTO
     * @throws ResourceNotFoundException si le produit n'existe pas
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id: " + id));
        return convertToDTO(product);
    }

    /**
     * Crée un nouveau produit.
     * @param request Données du produit à créer
     * @return Le produit créé sous forme de DTO
     */
    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = new Product();
        return getProductDTO(request, product);
    }

    private ProductDTO getProductDTO(CreateProductRequest request, Product product) {
        product.setNom(request.getNom());
        product.setDescription(request.getDescription());
        product.setPrix(request.getPrix());
        product.setQuantiteStock(request.getQuantiteStock());
        product.setCategorie(request.getCategorie());
        product.setActif(request.getActif());

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    /**
     * Met à jour un produit existant.
     * @param id Identifiant du produit à mettre à jour
     * @param request Nouvelles données du produit
     * @return Le produit mis à jour sous forme de DTO
     * @throws ResourceNotFoundException si le produit n'existe pas
     */
    public ProductDTO updateProduct(Long id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id: " + id));

        return getProductDTO(request, product);
    }

    /**
     * Supprime un produit.
     * @param id Identifiant du produit à supprimer
     * @throws ResourceNotFoundException si le produit n'existe pas
     */
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit non trouvé avec l'id: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Recherche des produits par catégorie.
     * @param categorie Catégorie recherchée
     * @return Liste des produits de cette catégorie
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategorie(String categorie) {
        return productRepository.findByCategorie(categorie)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recherche des produits actifs.
     * @return Liste des produits actifs
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getActiveProducts() {
        return productRepository.findByActif(true)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convertit une entité Product en ProductDTO.
     * @param product L'entité à convertir
     * @return Le DTO correspondant
     */
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getNom(),
                product.getDescription(),
                product.getPrix(),
                product.getQuantiteStock(),
                product.getCategorie(),
                product.getActif(),
                product.getDateCreation(),
                product.getDateModification()
        );
    }
}
