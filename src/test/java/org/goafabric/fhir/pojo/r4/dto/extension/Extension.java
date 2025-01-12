package org.goafabric.fhir.pojo.r4.dto.extension;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Extension {
    private String url;
    private String valueString; //also seems to be StringType with value field
}
