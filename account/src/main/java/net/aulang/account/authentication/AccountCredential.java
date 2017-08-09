package net.aulang.account.authentication;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.authentication.UsernamePasswordCredential;

/**
 * 用户凭证
 */
public class AccountCredential extends UsernamePasswordCredential {
	private static final long serialVersionUID = 1L;
	/**
	 * 账号ID
	 */
	private String id;
	/**
	 * 验证码
	 */
	private String captcha;

	public AccountCredential() {
	}

	public AccountCredential(String username, String password) {
		this(null, username, password);
	}

	public AccountCredential(String id, String username, String password) {
		super(username, password);
		this.id = id;
	}

	@Override
	public String getId() {
		if (id != null) {
			return id;
		} else {
			return getUsername();
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@Override
	public String toString() {
		return getId();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final AccountCredential that = (AccountCredential) o;

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}

		if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null) {
			return false;
		}

		if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null) {
			return false;
		}

		if (this.captcha != null ? !this.captcha.equals(that.captcha) : that.captcha != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(getUsername()).append(getPassword()).append(captcha)
				.toHashCode();
	}
}