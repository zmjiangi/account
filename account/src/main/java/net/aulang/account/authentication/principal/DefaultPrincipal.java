package net.aulang.account.authentication.principal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apereo.cas.authentication.principal.Principal;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultPrincipal implements Principal {
    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String id;

    private Map<String, Object> attributes;

    public DefaultPrincipal(String id) {
        this(id, new HashMap<>());
    }

    @JsonCreator
    protected DefaultPrincipal(@JsonProperty("id") String id,
                               @JsonProperty("attributes") Map<String, Object> attributes) {

        Assert.hasText(id, "Principal id cannot be empty");

        this.id = id;
        if (attributes == null) {
            this.attributes = new HashMap<>();
        } else {
            this.attributes = attributes;
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public String getId() {
        return this.id;
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
        final DefaultPrincipal rhs = (DefaultPrincipal) obj;
        return this.id.equals(rhs.id);
    }

    public Object setAttribute(String key, Object value) {
        return this.attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }
}