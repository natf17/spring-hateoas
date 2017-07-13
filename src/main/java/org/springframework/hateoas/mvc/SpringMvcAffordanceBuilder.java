/*
 * Copyright 2017 the original author or authors.
 *
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
 */
package org.springframework.hateoas.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.AffordanceModelFactory;
import org.springframework.hateoas.core.DummyInvocationUtils.MethodInvocation;
import org.springframework.hateoas.core.MappingDiscoverer;
import org.springframework.http.MediaType;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponents;

/**
 * Construct {@link SpringMvcAffordance}s using a collection of {@link AffordanceModelFactory}s.
 * 
 * @author Greg Turnquist
 */
public class SpringMvcAffordanceBuilder {

	private final PluginRegistry<? extends AffordanceModelFactory, MediaType> factories;

	public SpringMvcAffordanceBuilder(PluginRegistry<? extends AffordanceModelFactory, MediaType> factories) {

		Assert.notNull(factories, "Registry of LinkDiscoverer must not be null!");
		this.factories = factories;
	}

	/**
	 * Use the attributes of the current method call along with a collection of {@link AffordanceModelFactory}'s to
	 * create a set of {@link Affordance}s.
	 * 
	 * @param invocation
	 * @param discoverer
	 * @param components
	 * @return
	 */
	public List<Affordance> create(MethodInvocation invocation, MappingDiscoverer discoverer, UriComponents components) {

		Method method = invocation.getMethod();
		String[] httpMethods = discoverer.getRequestType(invocation.getTargetType(), method);

		List<Affordance> affordances = new ArrayList<Affordance>();

		for (String requestMethod : httpMethods) {

			SpringMvcAffordance springMvcAffordance = new SpringMvcAffordance(RequestMethod.valueOf(requestMethod), invocation.getMethod());

			for (AffordanceModelFactory factory : factories) {
				springMvcAffordance.addAffordanceModel(factory.getMediaType(), factory.getAffordanceModel(springMvcAffordance, invocation, components));
			}

			affordances.add(springMvcAffordance);
		}

		return affordances;
	}
}
