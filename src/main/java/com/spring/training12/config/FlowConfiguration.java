package com.spring.training12.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.DelegatingFlowViewResolver;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandler;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

@Configuration
public class FlowConfiguration extends AbstractFlowConfiguration {

	@Autowired
	MvcConfig mvcConfig;
	
	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder()
				.addFlowLocation("/WEB-INF/views/flow/sample-flow.xml", "sampleFlow")
				.build();
	}

	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry()).build();
	}

	@Bean
	public FlowHandlerAdapter flowHandler() throws Exception {
		FlowHandlerAdapter adapter = new FlowHandlerAdapter();
		adapter.setFlowExecutor(flowExecutor());
		adapter.afterPropertiesSet();
		return adapter;
	}
	
	@Bean
	public FlowHandlerMapping flowMapping() {
		FlowHandlerMapping mapping = new FlowHandlerMapping();
		mapping.setFlowRegistry(flowRegistry());
		mapping.setOrder(-1);
		return mapping;
	}
	
	@Bean
	public FlowBuilderServices flowBuilder() {
		return getFlowBuilderServicesBuilder().build();
	}
	
	@Bean
	public MvcViewFactoryCreator mvcViewFactory() {
		MvcViewFactoryCreator viewFactory = new MvcViewFactoryCreator();
		viewFactory.setViewResolvers(Arrays.asList(mvcConfig.viewResolver()));
		return viewFactory;
	}
	
}
