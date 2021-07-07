package org.goafabric.example.fhir.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jaxrs.server.AbstractJaxRsResourceProvider;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import org.goafabric.example.fhir.logic.BundleLogic;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BundleService extends AbstractJaxRsResourceProvider<Bundle> {
    @Autowired
    private BundleLogic bundleLogic;

    public BundleService(FhirContext fhirContext) {
        super(fhirContext);
    }

    @Override
    public Class<Bundle> getResourceType() {
        return Bundle.class;
    }

    @Read
    public Bundle getBundle(@IdParam final IdType idType) {
        return bundleLogic.getBundle(idType);
    }

    @Create
    public MethodOutcome createBundle(@ResourceParam Bundle bundle) {
        return bundleLogic.createBundle(bundle);
    }
}
