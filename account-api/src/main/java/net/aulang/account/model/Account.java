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

	private String id;
	private String username;
	private String password;

	private boolean mustChangePassword;

	public Account() {
	}

	public Account(String id) {
		this.id = id;
	}

	public Account(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public boolean getMustChangePassword() {
		return mustChangePassword;
	}

	public void setMustChangePassword(boolean mustChangePassword) {
		this.mustChangePassword = mustChangePassword;
	}
}
