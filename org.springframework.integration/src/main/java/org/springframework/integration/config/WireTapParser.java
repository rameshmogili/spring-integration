/*
 * Copyright 2002-2008 the original author or authors.
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

package org.springframework.integration.config;

import org.w3c.dom.Element;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.ConfigurationException;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.util.StringUtils;

/**
 * Parser for the &lt;wire-tap&gt; element.
 * 
 * @author Mark Fisher
 */
public class WireTapParser implements BeanDefinitionRegisteringParser {

	public String parse(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WireTap.class);
		String targetRef = element.getAttribute("channel");
		if (!StringUtils.hasText(targetRef)) {
			throw new ConfigurationException("the 'channel' attribute is required");
		}
		builder.addConstructorArgReference(targetRef);
		String selectorRef = element.getAttribute("selector");
		if (StringUtils.hasText(selectorRef)) {
			builder.addConstructorArgReference(selectorRef);
		}
		String timeout = element.getAttribute("timeout");
		if (StringUtils.hasText(timeout)) {
			builder.addPropertyValue("timeout", Long.parseLong(timeout));
		}
		return BeanDefinitionReaderUtils.registerWithGeneratedName(
				builder.getBeanDefinition(), parserContext.getRegistry());
	}

}
