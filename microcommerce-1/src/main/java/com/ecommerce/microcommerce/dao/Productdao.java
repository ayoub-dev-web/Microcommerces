package com.ecommerce.microcommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.microcommerce.model.Product;

@Repository
public interface Productdao extends JpaRepository<Product, Integer> {
	
	
	
 Product findById(int id);
 
List<Product> findByPrixGreaterThan(int prix);

List<Product> findByNomLike(String nom);


/* @Query(value= "SELECT * FROM Product  WHERE prix >=?1" , nativeQuery=true)
 List<Product> Afficheage( int prix);*/
 

@Query(value= "SELECT * FROM Product order by nom asc" , nativeQuery=true)
List<Product> findAllOrderByNomAsc();

	

}
