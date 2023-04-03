package com.example.vitasoft_task.configurations;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class StringPropertiesConfigurations {

    @Bean
    public PropertiesFactoryBean propertiesfilemapping() {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setFileEncoding("UTF-8");
        factoryBean.setLocation(new ClassPathResource("string.properties"));
        return factoryBean;
    }

}
