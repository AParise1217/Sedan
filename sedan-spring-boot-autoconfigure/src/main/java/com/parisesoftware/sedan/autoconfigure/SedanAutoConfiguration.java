package com.parisesoftware.sedan.autoconfigure;

import com.parisesoftware.sedan.SedanDriver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(SedanDriver.class)
public class SedanAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SedanDriver sedanDriver() {
        return new SedanDriver();
    }
}
