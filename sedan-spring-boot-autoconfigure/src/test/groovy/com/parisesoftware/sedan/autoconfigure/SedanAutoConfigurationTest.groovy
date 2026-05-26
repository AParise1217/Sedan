package com.parisesoftware.sedan.autoconfigure

import com.parisesoftware.sedan.SedanDriver
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.lang.Specification

class SedanAutoConfigurationTest extends Specification {

    def "auto-configures a SedanDriver bean"() {
        given:
        def ctx = new AnnotationConfigApplicationContext(SedanAutoConfiguration)

        expect:
        ctx.getBean(SedanDriver) != null

        cleanup:
        ctx.close()
    }

    def "auto-configured SedanDriver is a singleton — only one bean registered"() {
        given:
        def ctx = new AnnotationConfigApplicationContext(SedanAutoConfiguration)

        expect:
        ctx.getBeanNamesForType(SedanDriver).length == 1

        cleanup:
        ctx.close()
    }

    def "@ConditionalOnMissingBean: user-defined bean takes precedence"() {
        given:
        def ctx = new AnnotationConfigApplicationContext(UserConfig, SedanAutoConfiguration)

        expect:
        ctx.getBeanNamesForType(SedanDriver).length == 1
        ctx.getBean(SedanDriver).is(UserConfig.CUSTOM_INSTANCE)

        cleanup:
        ctx.close()
    }

    @Configuration
    static class UserConfig {
        static final SedanDriver CUSTOM_INSTANCE = new SedanDriver()

        @Bean
        SedanDriver sedanDriver() {
            return CUSTOM_INSTANCE
        }
    }
}
