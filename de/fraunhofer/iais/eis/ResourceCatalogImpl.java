package de.fraunhofer.iais.eis;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import de.fraunhofer.iais.eis.util.*;

/**
 * Default implementation of package de.fraunhofer.iais.eis.ResourceCatalog
 * 
 * Class that aggregates Resources from a (distributable) Catalog.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("ids:ResourceCatalog")
public class ResourceCatalogImpl implements ResourceCatalog {

    @JsonProperty("@id")
    @JsonAlias({"@id", "id"})
    @NotNull
    protected URI id;

    // List of all labels of this class
    @JsonIgnore
    protected List<TypedLiteral> label = Arrays.asList(new TypedLiteral("Resource Catalog", "en"));

    // List of all comments of this class
    @JsonIgnore
    protected List<TypedLiteral> comment =
        Arrays.asList(new TypedLiteral("Class that aggregates Resources from a (distributable) Catalog.", "en"));

    // all classes have a generic property array
    @JsonIgnore
    protected Map<String, Object> properties;

    // instance fields as derived from the IDS Information Model ontology

    @JsonAlias({"ids:offeredResource", "offeredResource"})
    protected List<Resource> _offeredResource = new ArrayList<>();

    @JsonAlias({"ids:offeredResource", "offeredResource"})
    protected List<URI> _offeredResourceAsUri = new ArrayList<>();

    @JsonAlias({"ids:requestedResource", "requestedResource"})
    protected List<Resource> _requestedResource = new ArrayList<>();

    @JsonAlias({"ids:requestedResource", "requestedResource"})
    protected List<URI> _requestedResourceAsUri = new ArrayList<>();

    protected ResourceCatalogImpl() {
        id = VocabUtil.getInstance().createRandomUrl("resourceCatalog");
    }

    @JsonProperty("@id")
    final public URI getId() {
        return id;
    }

    public String toRdf() {
        return VocabUtil.getInstance().toRdf(this);
    }

    @Override
    public String toString() {
        return this.toRdf();
    }

    public List<TypedLiteral> getLabel() {
        return this.label;
    }

    public List<TypedLiteral> getComment() {
        return this.comment;
    }

    // getter and setter for generic property map
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        if (this.properties == null)
            return null;
        Iterator<String> iter = this.properties.keySet().iterator();
        Map<String, Object> resultset = new HashMap<String, Object>();
        while (iter.hasNext()) {
            String key = iter.next();
            resultset.put(key, urifyObjects(this.properties.get(key)));
        }
        return resultset;
    }

    public Object urifyObjects(Object value) {
        if (value instanceof String && value.toString().startsWith("http")) {
            try {
                value = new URI(value.toString());
            } catch (Exception e) {
                /* do nothing */ }
        } else if (value instanceof ArrayList) {
            ArrayList<Object> result_array = new ArrayList<Object>();
            ((ArrayList) value).forEach(x -> result_array.add(urifyObjects(x)));
            return result_array;
        } else if (value instanceof Map) {
            Map<String, Object> result_map = new HashMap<String, Object>();
            ((Map) value).forEach((k, v) -> result_map.put(k.toString(), urifyObjects(v)));
            return result_map;
        }
        return value;
    }

    @JsonAnySetter
    public void setProperty(String property, Object value) {
        if (this.properties == null)
            this.properties = new HashMap<String, Object>();
        if (property.startsWith("@")) {
            return;
        } ;
        this.properties.put(property, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._offeredResource,
            this._offeredResourceAsUri,
            this._requestedResource,
            this._requestedResourceAsUri);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            ResourceCatalogImpl other = (ResourceCatalogImpl) obj;
            return Objects.equals(this._offeredResource, other._offeredResource) &&
                Objects.equals(this._offeredResourceAsUri, other._offeredResourceAsUri) &&
                Objects.equals(this._requestedResource, other._requestedResource) &&
                Objects.equals(this._requestedResourceAsUri, other._requestedResourceAsUri);
        }
    }

    @Override
    public ResourceCatalog deepCopy() {
        ResourceCatalogBuilder builder = new ResourceCatalogBuilder();
        for (Resource item : this._offeredResource) {
            if (item != null) {
                builder._offeredResource_(item.deepCopy());
            }
        }
        for (URI item : this._offeredResourceAsUri) {
            if (item != null) {
                builder._offeredResourceAsUri_(URI.create(item.toString()));
            }
        }
        for (Resource item : this._requestedResource) {
            if (item != null) {
                builder._requestedResource_(item.deepCopy());
            }
        }
        for (URI item : this._requestedResourceAsUri) {
            if (item != null) {
                builder._requestedResourceAsUri_(URI.create(item.toString()));
            }
        }
        return builder.build();
    }

    // accessor method implementations as derived from the IDS Information Model ontology

    @Override
    public List<Resource> getOfferedResource() {
        return _offeredResource;
    }

    @Override
    public void setOfferedResource(List<Resource> _offeredResource_) {
        this._offeredResource = _offeredResource_;
        this._offeredResourceAsUri = new ArrayList<>();
    }

    @Override
    public List<URI> getOfferedResourceAsUri() {
        return _offeredResourceAsUri;
    }

    @Override
    public void setOfferedResourceAsUri(List<URI> _offeredResource_) {
        this._offeredResourceAsUri = _offeredResource_;
        this._offeredResource = new ArrayList<>();
    }

    /**
     * Helper function for (de-)serialization of the _resourceCatalog and the _resourceCatalogAsfields.
     *
     * @return Returns the a UriOrModelClass object with the content of the field or null if the field
     *         is not set.
     */
    @Override
    public UriOrModelClass getOfferedResourceAsObject() {
        if (!_offeredResourceAsUri.isEmpty()) {
            return new UriOrModelClass(_offeredResourceAsUri);
        } else if (!_offeredResource.isEmpty()) {
            return new UriOrModelClass(_offeredResource);
        } else {
            return null;
        }
    }

    @Override
    public List<Resource> getRequestedResource() {
        return _requestedResource;
    }

    @Override
    public void setRequestedResource(List<Resource> _requestedResource_) {
        this._requestedResource = _requestedResource_;
        this._requestedResourceAsUri = new ArrayList<>();
    }

    @Override
    public List<URI> getRequestedResourceAsUri() {
        return _requestedResourceAsUri;
    }

    @Override
    public void setRequestedResourceAsUri(List<URI> _requestedResource_) {
        this._requestedResourceAsUri = _requestedResource_;
        this._requestedResource = new ArrayList<>();
    }

    /**
     * Helper function for (de-)serialization of the _resourceCatalog and the _resourceCatalogAsfields.
     *
     * @return Returns the a UriOrModelClass object with the content of the field or null if the field
     *         is not set.
     */
    @Override
    public UriOrModelClass getRequestedResourceAsObject() {
        if (!_requestedResourceAsUri.isEmpty()) {
            return new UriOrModelClass(_requestedResourceAsUri);
        } else if (!_requestedResource.isEmpty()) {
            return new UriOrModelClass(_requestedResource);
        } else {
            return null;
        }
    }

}
