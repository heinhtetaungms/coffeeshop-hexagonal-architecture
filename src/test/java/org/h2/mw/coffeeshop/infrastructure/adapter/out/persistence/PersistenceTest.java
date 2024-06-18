package org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@ComponentScan("org.h2.mw.coffeeshop.infrastructure.adapter.out.persistence")
public @interface PersistenceTest {
}
