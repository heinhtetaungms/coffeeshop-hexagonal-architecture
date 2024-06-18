package org.h2.mw.coffeeshop.infrastructure.config;

import org.h2.mw.coffeeshop.architecture.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "org.h2.mw.coffeeshop.core.application",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = UseCase.class)
)
public class DomainConfig {
}
