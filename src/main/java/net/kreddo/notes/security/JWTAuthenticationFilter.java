package net.kreddo.notes.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.kreddo.notes.config.NotesApiProperties;
import net.kreddo.notes.controller.dto.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
    try {
      var userDto = new ObjectMapper().readValue(req.getInputStream(), UserDto.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword(), new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {
    var notesApiProperties = getNotesApiProperties(req);

    var token = JWT.create()
        .withSubject(((User) auth.getPrincipal()).getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + notesApiProperties.getJwt().getExpirationTime()))
        .sign(HMAC512(notesApiProperties.getJwt().getSecret().getBytes()));

    res.addHeader(notesApiProperties.getJwt().getHeaderText(), notesApiProperties.getJwt().getTokenPrefix() + token);
  }

  private NotesApiProperties getNotesApiProperties(HttpServletRequest req) {
    var servletContext = req.getServletContext();
    var webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    NotesApiProperties notesApiProperties = null;
    if (webApplicationContext != null) {
      notesApiProperties = webApplicationContext.getBean(NotesApiProperties.class);
    }
    return notesApiProperties;
  }
}