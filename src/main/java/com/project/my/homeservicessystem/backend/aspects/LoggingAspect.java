package com.project.my.homeservicessystem.backend.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.project.my.homeservicessystem.backend.services.*.*(..) ) || " +
            "execution(* com.project.my.homeservicessystem.backend.utilities.*.*(..))")
    public Object logAroundAll(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(
                joinPoint.getTarget().getClass().getSimpleName() + ": " +
                        joinPoint.getSignature().getName() + " called"
        );

        try {
            Object proceed = joinPoint.proceed();
            logger.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), proceed);
            return proceed;
        } catch (Throwable e) {
            logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

}
