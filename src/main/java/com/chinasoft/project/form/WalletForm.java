package com.chinasoft.project.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WalletForm {
	@NotNull
	String itcode;
	@NotNull
	@Size(max = 100)
	String nickname;
	@NotNull
	String password;
	@NotNull
	String question;
	@NotNull
	String answer;
}
