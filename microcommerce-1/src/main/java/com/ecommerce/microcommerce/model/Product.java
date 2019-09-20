package com.ecommerce.microcommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;

@Entity
public class Product {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Length(min=3 , max=25 , message="Nom trop long ou trop court. Et oui messages sont plus stylés que ceux de Spring")
	private String nom;
	
	private int prix;
	private int prixAchat;
	
	
	public Product() {
		super();
		
		
	}


	public Product(int id, String nom, int prix , int prixAchat) {
		super();
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.prixAchat=prixAchat;
	}


	public int getId() {
		return id;
	}


	public String getNom() {
		return nom;
	}


	public int getPrix() {
		return prix;
	}
	
	public int getPrixAchat() {
		return prixAchat;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public void setPrix(int prix) {
		
		if (prix==0) {
			throw new ProduitGratuitException("c'est un produit n'est pas Gratuit");
		}
		else {
		
		this.prix = prix;
		}
	}
	
	public void setPrixAchat(int prixAchat) {
		this.prixAchat = prixAchat;
	}
	
	


	@Override
	public String toString() {
		return "Product [id=" + id + ", nom=" + nom + ", prix=" + prix + ", prixAchat=" + prixAchat + "]";
	}


	



	
	

}
