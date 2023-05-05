package com.main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

	public boolean isValid() {
		if((city !=null && !city.isBlank()) || (district !=null && !district.isBlank())
				|| (street !=null && !street.isBlank()) || (zipCode !=null && !zipCode.isBlank())) {
			return true;
		}
		return false;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "{address.city.violation}")
	private String city;
	@NotBlank(message = "{address.district.violation}")
	private String district;
	@NotBlank(message = "{address.street.violation}")
	private String street;
	@NotBlank(message = "{address.zipCode.violation}")
	private String zipCode;
	@OneToOne(mappedBy = "address")
	@JsonIgnoreProperties(value = {"address"})
	private Customer cust;
	@Override
	public String toString() {
		return "Address [id=" + id + ", city=" + city + ", district=" + district + ", street=" + street + ", zipCode="
				+ zipCode + "]";
	}
	
	
}
