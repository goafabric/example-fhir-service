package org.goafabric.fhir.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jaxrs.server.AbstractJaxRsResourceProvider;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import org.goafabric.fhir.logic.PractitionerLogic;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.stereotype.Component;

@Component
public class PractitionerController extends AbstractJaxRsResourceProvider<Practitioner> {
	private final PractitionerLogic practitionerLogic;

	public PractitionerController(FhirContext fhirContext, PractitionerLogic practitionerLogic) {
		super(fhirContext);
		this.practitionerLogic = practitionerLogic;
	}

	@Override
	public Class<Practitioner> getResourceType() {
		return Practitioner.class;
	}

	@Read
	public Practitioner getPractitioner(@IdParam final IdType idType) {
		return practitionerLogic.getPractitioner(idType);
	}

}
