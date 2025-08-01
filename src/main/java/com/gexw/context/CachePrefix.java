package com.gexw.context;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "gexw.g1")
@Data
@Configuration
public class CachePrefix {
    private String name;
    private String age;
    private String address;
    private  String prefix;
    private  String yewu;
    private  String fastServer;
}
