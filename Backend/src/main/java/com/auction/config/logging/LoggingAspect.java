package com.auction.config.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  private static final String CONTROLLER_CLASS_MDC_KEY = "controller";

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) && execution(* *(..))")
  public void anyControllerAction() {
    /* Nothing to do here. */
  }

  @Pointcut("within(@org.springframework.stereotype.Service *) && execution(* *(..))")
  public void anyServiceAction() {
    /* Nothing to do here. */
  }

  @Before("anyControllerAction()")
  public void beforeAnyController(JoinPoint joinPoint) {
    beforeAny(joinPoint);
  }

  @After("anyControllerAction()")
  public void afterAnyController() {
    MDC.remove(CONTROLLER_CLASS_MDC_KEY);
  }

  @Before("anyServiceAction()")
  public void beforeAnyService(JoinPoint joinPoint) {
    beforeAny(joinPoint);
  }

  @After("anyServiceAction()")
  public void afterAnyService() {
    MDC.remove(CONTROLLER_CLASS_MDC_KEY);
  }

  protected void beforeAny(@NonNull JoinPoint joinPoint) {
    String targetClass = joinPoint.getSignature().getDeclaringType().getSimpleName();
    MDC.put(CONTROLLER_CLASS_MDC_KEY, targetClass);
    log.debug("BeforeAnyController(): class invoked - " + targetClass);
  }
}