package org.goafabric.example.fhir.configuration;

/*-
 * #%L
 * hapi-fhir-spring-boot-autoconfigure
 * %%
 * Copyright (C) 2014 - 2021 Smile CDR, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jaxrs.server.AbstractJaxRsProvider;
import ca.uhn.fhir.rest.server.HardcodedServerAddressStrategy;
import ca.uhn.fhir.rest.server.IPagingProvider;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.IServerInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import javax.servlet.ServletException;
import java.util.List;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for HAPI FHIR.
 *
 * @author Mathieu Ouellet
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties(FhirProperties.class)
public class FhirAutoConfiguration {


	private final FhirProperties properties;

	public FhirAutoConfiguration(FhirProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public FhirContext fhirContext() {
		FhirContext fhirContext = new FhirContext(properties.getVersion());
		return fhirContext;
	}


	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(AbstractJaxRsProvider.class)
	@EnableConfigurationProperties(FhirProperties.class)
	@ConfigurationProperties("hapi.fhir.rest")
	@SuppressWarnings("serial")
	static class FhirRestfulServerConfiguration extends RestfulServer {

		private final FhirProperties properties;

		private final FhirContext fhirContext;

		private final List<IResourceProvider> resourceProviders;

		private final IPagingProvider pagingProvider;

		private final List<FhirRestfulServerCustomizer> customizers;

		public FhirRestfulServerConfiguration(
				FhirProperties properties,
				FhirContext fhirContext,
				ObjectProvider<List<IResourceProvider>> resourceProviders,
				ObjectProvider<IPagingProvider> pagingProvider,
				ObjectProvider<List<IServerInterceptor>> interceptors,
				ObjectProvider<List<FhirRestfulServerCustomizer>> customizers) {
			this.properties = properties;
			this.fhirContext = fhirContext;
			this.resourceProviders = resourceProviders.getIfAvailable();
			this.pagingProvider = pagingProvider.getIfAvailable();
			this.customizers = customizers.getIfAvailable();
		}

		@Bean
		public ServletRegistrationBean fhirServerRegistrationBean() {
			ServletRegistrationBean registration = new ServletRegistrationBean(this, this.properties.getServer().getPath());
			registration.setLoadOnStartup(1);
			return registration;
		}

		@Override
		protected void initialize() throws ServletException {
			super.initialize();

			setFhirContext(this.fhirContext);
			setResourceProviders(this.resourceProviders);
			setPagingProvider(this.pagingProvider);

			setServerAddressStrategy(new HardcodedServerAddressStrategy(this.properties.getServer().getPath()));

			customize();
			this.registerInterceptor(new TenantIdInterceptor());

			//ExceptionHandlingInterceptor interceptor = new ExceptionHandlingInterceptor();
			//registerInterceptor(new MyExceptionHandlingInterceptor());

		}
		
		private void customize() {
			if (this.customizers != null) {
				AnnotationAwareOrderComparator.sort(this.customizers);
				for (FhirRestfulServerCustomizer customizer : this.customizers) {
					customizer.customize(this);
				}
			}
		}

	}
	
}