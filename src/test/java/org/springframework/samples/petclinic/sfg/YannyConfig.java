package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

public class YannyConfig {

    @Bean
    YannyWordProducer yannyWordProducer() {
        return new YannyWordProducer();
    }
}
