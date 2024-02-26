package com.madhu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ExcelData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String market;

	private String country;

	private String product;

	private String discountBand;

	private Double unitsSold;

	private Double manufacturing;


}
