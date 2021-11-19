package com.auction.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@Order(2)
public class RequestResponseLoggingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
    /* Nothing to do here. */
  }

  @Override
  public void doFilter(
          ServletRequest servletRequest,
          ServletResponse servletResponse,
          FilterChain filterChain
  ) throws IOException, ServletException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      MDC.put("email", auth.getName());
    } else {
      MDC.put("email", "ANONYMOUS");
    }

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse res = (HttpServletResponse) servletResponse;

    log.info("[{}] request for URL: [{}]", req.getMethod(), req.getRequestURI());
    filterChain.doFilter(servletRequest, servletResponse);
    log.info("Response ==> [{}]", res.getContentType());
  }

  @Override
  public void destroy() {
    /* Nothing to do here. */
  }
}