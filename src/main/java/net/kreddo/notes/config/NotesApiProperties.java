package net.kreddo.notes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "notes-api")
public class NotesApiProperties {
  private final Jwt jwt = new Jwt();

  public Jwt getJwt() {
    return jwt;
  }

  @Getter
  @Setter
  public static class Jwt {
    private String secret;
    private Long expirationTime;
    private String tokenPrefix;
    private String headerText;
  }
}