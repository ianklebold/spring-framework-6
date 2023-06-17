package jedi.followmypath.webapp.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "host.api.ip2location")
public class PropertyHostLocation {

    /**
     * The url to connect to.
     */
    String url;

    // Getters and Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
