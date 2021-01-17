package net.kreddo.notes.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.kreddo.notes.config.NotesApiProperties;
import net.kreddo.notes.security.exception.InvalidTokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    var notesApiProperties = getNotesApiProperties(req);

    var header = req.getHeader(notesApiProperties.getJwt().getHeaderText());

    if (header != null && header.startsWith(notesApiProperties.getJwt().getTokenPrefix())) {
      SecurityContextHolder.getContext().setAuthentication(getAuthenticationToken(req, notesApiProperties).orElseThrow(InvalidTokenException::new));
    }

    chain.doFilter(req, res);
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

  private Optional<UsernamePasswordAuthenticationToken> getAuthenticationToken(HttpServletRequest request, NotesApiProperties notesApiProperties) {
    Optional<UsernamePasswordAuthenticationToken> authenticationToken = Optional.empty();
    AtomicReference<String> user = new AtomicReference<>();
    Optional.ofNullable(request.getHeader(notesApiProperties.getJwt().getHeaderText())).ifPresent(s ->
        user.set(JWT.require(Algorithm.HMAC512(notesApiProperties.getJwt().getSecret().getBytes())).build()
            .verify(s.replace(notesApiProperties.getJwt().getTokenPrefix(), ""))
            .getSubject())
    );
    if (user.get() != null) {
      authenticationToken = Optional.of(new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()));
    }
    return authenticationToken;
  }
}
