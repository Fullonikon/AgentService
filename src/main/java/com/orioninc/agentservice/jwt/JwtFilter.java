package com.orioninc.agentservice.jwt;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

import com.orioninc.agentservice.detail.CustomUserDetails;
import com.orioninc.agentservice.service.CustomUserDetailsService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@Log
public class JwtFilter extends GenericFilterBean {

  public static final String AUTHORIZATION = "Authorization";

  private final JwtProvider jwtProvider;

  private final CustomUserDetailsService customUserDetailsService;

  public JwtFilter(JwtProvider jwtProvider, CustomUserDetailsService customUserDetailsService) {
    this.jwtProvider = jwtProvider;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    String token = getTokenFromRequest((HttpServletRequest) servletRequest);
    if (token != null && jwtProvider.validateToken(token)) {
      String userLogin = jwtProvider.getLoginFromToken(token);
      CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
      if (nonNull(customUserDetails)) {
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearer = request.getHeader(AUTHORIZATION);
    if (hasText(bearer) && bearer.startsWith("Bearer ")) {
      return bearer.substring(7);
    }
    return null;
  }
}
