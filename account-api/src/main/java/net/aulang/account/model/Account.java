package net.aulang.account.model;

import java.io.Serializable;

/**
 * 账号
 * 
 * @author Aulang
 *
 */
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public Account() {
	}

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
