package org.goafabric.fhir.adapter;

import org.hl7.fhir.r4.model.Practitioner;

public interface PractitionerAdapter {
    Practitioner getPractitioner(String id);

}