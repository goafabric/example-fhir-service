package org.goafabric.fhir.extensions;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandler  {

    @Hook(Pointcut.SERVER_HANDLE_EXCEPTION)
    public boolean handleException(RequestDetails requestDetails, BaseServerResponseException exception, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(exception.getStatusCode());
        response.setContentType("text/plain");
        response.getWriter().append(exception.getMessage());
        response.getWriter().close();
        return false;
    }
}