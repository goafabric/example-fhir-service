
package org.goafabric.fhir.pojo.r4.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resourceType",
    "id",
    "meta",
    "identifier",
    "active",
    "name",
    "telecom",
    "address"
})
@Generated("jsonschema2pojo")
@Data
public class Organization {

    @JsonProperty("resourceType")
    public String resourceType;
    @JsonProperty("id")
    public String id;
    @JsonProperty("meta")
    public Meta meta;
    @JsonProperty("identifier")
    public List<Identifier> identifier = null;
    @JsonProperty("active")
    public Boolean active;
    @JsonProperty("name")
    public String name;
    @JsonProperty("telecom")
    public List<Telecom> telecom = null;
    @JsonProperty("address")
    public List<Address> address = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}