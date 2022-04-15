package com.romel.online.store.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "product")
@Entity(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
	@Column(name = "id_product")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 5, message = "{name-message}")
	@Column(name = "name", nullable = false)
	private String name;
	
	@Size(min = 5, message = "{code-message}")
	@Column(name = "code", nullable = false)
	private String code;
	
	@Positive(message = "{price-message}")
	@Column(name = "price", nullable = false)
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "id_category", nullable = false)
	private Category category;
	
	@Size(min = 4, max = 60, message = "{description-message}")
	@Column(name = "description", length = 60, nullable = false)
	private String description;

}
