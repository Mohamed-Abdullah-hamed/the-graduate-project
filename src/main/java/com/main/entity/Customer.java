package com.main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@NotBlank(message = "{name.violation}")
	private String name;
	@OneToOne()
	@JsonIgnoreProperties(value = { "cust" })
	@Valid
	private Address address;
	@OneToOne
	@JoinColumn(name = "cart_id")
	private ShoppingCart cart;
	@Transient
	private String userType;
	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value ={"cust"})
	@Valid
	private AppUser user;
	
	@Override
	public String toString() {
		return "AppUser [id=" + id + ", name=" + name + ", address=" + address + ", cart=" + cart + "]";
	}

}
