package com.main.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
	private long id;
	@Email(message = "{user.email.invalid.violation}")
	@NotBlank(message = "{user.email.blank.violation}")
	private String email;
	@NotBlank(message = "{user.password.blank.violation}")
	private String password;
	@Email(message = "{user.email.invalid.violation}")
	@NotBlank(message = "{user.email.blank.violation}")
	private String newEmail;
	@NotBlank(message = "{user.password.blank.violation}")
	private String newPassword;
	@NotBlank(message = "{user.password.blank.violation}")
	private String rePassword;

	public boolean isPassMatched() {
		return newPassword.equals(rePassword);
	}
}
