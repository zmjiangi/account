package net.aulang.account.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class OAuthClient implements ClientDetails {
    @Id
    private String id;
    @Field("client_id")
    @Indexed(unique = true, sparse = true)
    private String clientId;
    @Field("client_secret")
    private String clientSecret;
    @Field("redirect_uri")
    private Set<String> registeredRedirectUri;
    private Set<String> scope = Collections.emptySet();
    @Field("resource_ids")
    private Set<String> resourceIds = Collections.emptySet();
    @Field("authorized_grant_types")
    private Set<String> authorizedGrantTypes = Collections.emptySet();
    @Field("auto_approve_scopes")
    private Set<String> autoApproveScopes;
    private Set<GrantedAuthority> authorities = Collections.emptySet();
    @Field("access_token_validity")
    private Integer accessTokenValiditySeconds;
    @Field("refresh_token_validity")
    private Integer refreshTokenValiditySeconds;
    @Field("additional_information")
    private Map<String, Object> additionalInformation = new LinkedHashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
        this.registeredRedirectUri = registeredRedirectUri == null ? null : new LinkedHashSet<>(registeredRedirectUri);
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope == null ? Collections.emptySet()
                : new LinkedHashSet<>(scope);
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds == null ? Collections.emptySet() : new LinkedHashSet<>(resourceIds);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes == null ? Collections.emptySet() : new LinkedHashSet<>(authorizedGrantTypes);
    }

    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes == null ? Collections.emptySet() :
                new LinkedHashSet<>(autoApproveScopes);
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (autoApproveScopes == null) {
            return false;
        }
        for (String auto : autoApproveScopes) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities == null ? Collections.emptySet() : new LinkedHashSet<>(authorities);
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation == null ? new LinkedHashMap<>() : additionalInformation;
    }

    public void addAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    }
}
