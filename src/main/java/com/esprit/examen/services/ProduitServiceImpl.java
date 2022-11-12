package com.esprit.examen.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.CategorieProduitRepository;
import com.esprit.examen.repositories.ProduitRepository;
import com.esprit.examen.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProduitServiceImpl implements IProduitService {

	@Autowired
	ProduitRepository produitRepository;
	@Autowired
	StockRepository stockRepository;
	@Autowired
	CategorieProduitRepository categorieProduitRepository;

	@Override
	public List<Produit> retrieveAllProduits() {
		List<Produit> produits = produitRepository.findAll();
		for (Produit produit : produits) {
			log.info(" Produit : " + produit);
		}
		return produits;
	}

	@Transactional
	public Produit addProduit(Produit p) {
		produitRepository.save(p);
		log.info(" Produit ajouté: " + p);
		return p;
	}

	@Override
	public void deleteProduit(Long produitId) {
		try {
			produitRepository.deleteById(produitId);
			log.info(" Produit a ete supprimée ");
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public Produit updateProduit(Produit p) {
		try {
			produitRepository.save(p);
			log.info(" Produit a ete modifié ");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return p;
	}

	@Override
	public Produit retrieveProduit(Long produitId) {
		Produit produit = produitRepository.findById(produitId).orElse(null);
		log.info("produit :" + produit);
		return produit;
	}

	@Override
	public void assignProduitToStock(Long idProduit, Long idStock) {
		Produit produit = produitRepository.findById(idProduit).orElse(null);
		Stock stock = stockRepository.findById(idStock).orElse(null);
		try {
			produit.setStock(stock);
			log.info(" Produit et tck a ete assignée ");
			produitRepository.save(produit);
			log.info(" Produit a ete enregistrée ");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

}