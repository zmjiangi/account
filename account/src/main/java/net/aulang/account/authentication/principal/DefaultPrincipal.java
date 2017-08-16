package net.aulang.account.authentication.principal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apereo.cas.authentication.principal.Principal;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
        final Map<String, Object> attrs = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        attrs.putAll(this.attributes);
        return attrs;
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.id);
        return builder.toHashCode();
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
