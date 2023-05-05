package com.main.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@Email(message = "{user.email.invalid.violation}")
	@NotBlank(message = "{user.email.blank.violation}")
	private String email;
	@NotBlank(message = "{user.password.blank.violation}")
	private String password;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") },
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@JsonIgnoreProperties(value = {"users"})
	private Set<Role> roles = new HashSet<>();
	@OneToOne(mappedBy = "user")
	@JsonIgnoreProperties(value= {"user","ratings"})
	private Customer cust;
	public AppUser(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public void addRole(Role role) {
		this.roles.add(role);
		role.getUsers().add(this);
	}
	public void removeRole(Role role) {
		this.roles.remove(role);
		role.getUsers().remove(this);
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, id, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppUser other = (AppUser) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(password, other.password);
	}

	
}
