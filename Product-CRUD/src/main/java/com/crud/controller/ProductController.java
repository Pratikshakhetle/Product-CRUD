package com.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.crud.Repository.CategoryRepository;
import com.crud.Repository.ProductRepository;
import com.crud.model.Product;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
 @Autowired
 private ProductRepository productRepository;
 
 @Autowired
 private CategoryRepository categoryRepository;
 
 
 @GetMapping("/products")
 public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
         							 @RequestParam(defaultValue = "10") int size) {
	 Pageable pageable = PageRequest.of(page, size);
	 return productRepository.findAll(pageable);
}
 @PostMapping("/products")
 public String createProduct(@RequestBody Product product) {
	 productRepository.save(product);
	 return "Product Created in Database";
 }
 @GetMapping("products/{id}")
 public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
     Optional<Product>prod=productRepository.findById(id);
     if (prod.isPresent()) {
         return ResponseEntity.ok(prod.get());
     } else {
         return ResponseEntity.notFound().build();
     }
 }
 @PutMapping("/product/{id}")
 public String updateProductById(@PathVariable Integer id, @RequestBody Product product) {
	 Optional<Product>prod=productRepository.findById(id);
     if (prod.isPresent()) {
         Product existProd=prod.get();
         existProd.setName(product.getName());
         existProd.setPrice(product.getPrice());
         productRepository.save(existProd);
     return "Product Details against Id" +id + "updated";
     }
     else {
     return "Product Details does not exist for Id" +id;
     }
 }

 @DeleteMapping("/product/{id}")
 public String deleteProductById(@PathVariable Integer id) {
	 productRepository.deleteById(id);
     return "Product Deleted Successfully";
 }
}


