package org.goafabric.fhir.adapter;

import org.hl7.fhir.r4.model.Patient;

public interface PatientAdapter {
    Patient getPatient(String id);
}
