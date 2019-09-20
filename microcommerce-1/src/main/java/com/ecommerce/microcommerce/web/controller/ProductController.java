package com.ecommerce.microcommerce.web.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.Productdao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api("API pour es opérations CRUD sur les produits.")
@RestController
@RequestMapping("/Products")
public class ProductController {
	

	@Autowired
	private Productdao productdao;
	
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
	@GetMapping("/{id}")
	public Product fidbyid(@PathVariable int id)
	{
		Product product= productdao.findById(id);
		
		if (product==null) {
			
			throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
		}
		
		return product;
	}
	
	/*@PostMapping("/add")
	public ResponseEntity<void> ajouter(@Valid @RequestBody Product product)
	{
		   Product productadd = productdao.save(product);
		   
		   if (productadd==null) {
			   
			   return ResponseEntity.noContent().build();
		}
		   URI location = ServletUriComponentsBuilder
				   .fromCurrentRequest()
				   .path("/{id}")
				   .buildAndExpand(productadd.getId())
				   .toUri();
		   
		   return ResponseEntity.created(location).build();
		
	}*/
	
    @PostMapping(value = "/add")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {
    	
        Product productAdded =  productdao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
		
    }
	
	   @GetMapping(value = {"","/"})
	    public MappingJacksonValue listeProduits() {
	        Iterable<Product> produits = productdao.findAll();

	        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

	        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

	        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

	        produitsFiltres.setFilters(listDeNosFiltres);

	        return produitsFiltres ;
	    }

	   @GetMapping(value = "prixLimit/{prix}")
		public List<Product> testeDeRequetes(@PathVariable int prix) {
		    return productdao.findByPrixGreaterThan(prix);
		}
	    
	   @GetMapping(value = "/like/{nom}")
	    public List<Product> afficherUnProduit(@PathVariable String nom) {
	        return productdao.findByNomLike("%"+nom+"%");
	    }
	   
	   @PutMapping("/update/{id}")
	   public Product update(@RequestBody Product product )
	   {
		   return productdao.save(product);
	   }
	   
	    
	    @GetMapping("/AdminProduits")
	  public List<String> calculerMargeProduit ()
	  {
		  
		  
		  List<Product> lis = new ArrayList<Product>();
	    	productdao.findAll().forEach(lis :: add);
	    	List<String> str = new ArrayList<String>();
	    	
	        int k=0 ;
	    	for (int i = 0; i < lis.size(); i++) {
				Product p =lis.get(i);
	    		
				k= p.getPrix()-p.getPrixAchat();
				
				if (k != 0) {			
					String f = "Product {id = "+p.getId()+", nom = "+p.getNom()+" , prix = "+p.getPrix()+"} : " +k;
					str.add(f);
				}
				
				
			}
		  
		 return str;
	  }
	    
	    @GetMapping("/Trier")
	    public List<Product> trierProduitsParOrdreAlphabetique ()
	    {
	    	return productdao.findAllOrderByNomAsc();
	    }
	   
	   

}
