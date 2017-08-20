package net.aulang.account.authentication;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class PasswordChangeBean implements Serializable {
    private static final long serialVersionUID = 1;

    private String password;

    private String confirmedPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(final String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final PasswordChangeBean rhs = (PasswordChangeBean) obj;
        return new EqualsBuilder()
                .append(this.password, rhs.password)
                .append(this.confirmedPassword, rhs.confirmedPassword)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(password)
                .append(confirmedPassword)
                .toHashCode();
    }
}