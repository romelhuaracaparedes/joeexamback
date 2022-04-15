package com.romel.online.store.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "category")
@Entity(name = "Category")
public class Category {
	
	
	@Id
	@Column(name = "id_category")
	protected Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "code")
	private int code;
		

}
