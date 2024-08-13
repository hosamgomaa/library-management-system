package org.task.maid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.task.maid.config.RsaKeyConfigProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
@EnableCaching
public class MaidApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaidApplication.class, args);
    }
}
