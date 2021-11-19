package com.auction.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Order(1)
public class TransactionLoggingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
    /* Nothing to do in here */
  }

  @Override
  public void doFilter(
          ServletRequest servletRequest,
          ServletResponse servletResponse,
          FilterChain filterChain
  ) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) servletRequest;

    log.info("Starting transaction for request: [{}]", req.getRequestURI());
    filterChain.doFilter(servletRequest, servletResponse);
    log.info("Committing transaction for request: [{}]", req.getRequestURI());
  }

  @Override
  public void destroy() {
    /* Nothing to do in here */
  }
}
