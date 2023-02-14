
package org.goafabric.fhir.controller.dto.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Software {

    @JsonProperty("name")
    public String name;
    @JsonProperty("version")
    public String version;

}