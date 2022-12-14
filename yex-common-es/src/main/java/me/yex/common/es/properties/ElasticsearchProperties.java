package me.yex.common.es.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.es.properties
 */

@Data
@ConfigurationProperties(prefix = "zn.es")
public class ElasticsearchProperties {
    /**
     * Comma-separated list of the Elasticsearch instances to use.
     */
    private List<String> uris = new ArrayList<>(Collections.singletonList("http://localhost:9200"));

    /**
     * Credentials username.
     */
    private String username;

    /**
     * Credentials password.
     */
    private String password;

    /**
     * Connection timeout.
     */
    private Duration connectionTimeout = Duration.ofSeconds(1);

    /**
     * Read timeout.
     */
    private Duration readTimeout = Duration.ofSeconds(30);
}
