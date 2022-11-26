package com.example.enterprise_internet_applications_project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.enterprise_internet_applications_project.services..*(..))")
    public void forServiceLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication!=null?authentication.getPrincipal().toString():"User";

        if (args.length > 0)
            logger.info("{} invoke {} with input {}", userName, methodName, args);
        else
            logger.info("{} invoke {}", userName, methodName);

    }
}
