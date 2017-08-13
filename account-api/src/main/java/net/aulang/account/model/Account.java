package net.aulang.account.model;

import net.aulang.account.common.AccountStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账号
 *
 * @author Aulang
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private int status = AccountStatus.Enabled.state;

    @Indexed(unique = true, sparse = true)
    private String username;
    @Indexed(unique = true, sparse = true)
    private String email;
    @Indexed(unique = true, sparse = true)
    private String mobile;

    private String password;

    private String nickname;
    private List<String> emials;
    private List<String> mobiles;
    private Map<String, String> securityQuestions;

    private Date lastChangedPasswordTime;
    private boolean mustChangePassword = false;
    private Date creationTime = new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getEmials() {
        return emials;
    }

    public void setEmials(List<String> emials) {
        this.emials = emials;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public Map<String, String> getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(Map<String, String> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public Date getLastChangedPasswordTime() {
        return lastChangedPasswordTime;
    }

    public void setLastChangedPasswordTime(Date lastChangedPasswordTime) {
        this.lastChangedPasswordTime = lastChangedPasswordTime;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getPrimaryEmail() {
        if (email != null) {
            return email;
        }

        if (emials != null && emials.size() != 0) {
            return emials.get(0);
        }
        return null;
    }

    public String getPrimaryMobile() {
        if (mobile != null) {
            return mobile;
        }

        if (mobiles != null && mobiles.size() != 0) {
            return mobiles.get(0);
        }
        return null;
    }
}
