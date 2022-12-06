package com.example.enterprise_internet_applications_project.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
//
//    @Around("execution(* com.example.enterprise_internet_applications_project.services..*(..))")
//    public Object forServiceLog(ProceedingJoinPoint joinPoint) {
//        String methodName = joinPoint.getSignature().toShortString();
//        Object[] args = joinPoint.getArgs();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication!=null?authentication.getPrincipal().toString():"User";
//
//        if (args.length > 0)
//            logger.info("{} invoke {} with input {}", userName, methodName, args);
//        else
//            logger.info("{} invoke {}", userName, methodName);
//
//        try {
//            return joinPoint.proceed();
//        } catch (Throwable e) {
//            e.printStackTrace();
//            return e;
//        }
//    }




    @Before("execution(* com.example.enterprise_internet_applications_project.services..*(..))")
    public void logRequest(JoinPoint joinPoint){
        logger.info(joinPoint.getSignature().getName());
        logger.info(joinPointForLogs(joinPoint,"REQUEST"));
    }


    @AfterReturning("execution(* com.example.enterprise_internet_applications_project.services..*(..))")
    public void logsResponse(JoinPoint joinPoint){
        logger.info(joinPoint.getSignature().getName());
        logger.info(joinPointForLogs(joinPoint,"RESPONSE"));
    }
    private String joinPointForLogs(JoinPoint joinPoint, String dataType) {

        if (joinPoint.getArgs().length < 1) {
            return joinPoint.getSignature().getName()
                    .concat(" method don`t have parameters");
        }
        Object[] obj = joinPoint.getArgs();
        StringBuilder requestValue = new StringBuilder();
        if(dataType.compareTo("REQUEST")==0){
            requestValue.append("\r\n========== The request values ==========");
        }
        else {
            requestValue.append("\r\n========== The response values ==========");
        }
        Arrays.stream(obj).forEach(x -> {
            requestValue.append("\r\n");
            requestValue.append(x.toString());
        });
        requestValue
                .append("\r\n============= ======="
                        + "====== =============");
        return requestValue.toString();
    }


    /*@AfterThrowing(value = "execution(* com.example.enterprise_internet_applications_project...*(..))" ,throwing = "exception")
    public void logsErrors(JoinPoint joinPoint, Throwable exception){
        logger.info("========================== We have Error here ==========================");
        // for log the controller name
        logger.info(joinPoint.getSignature().getName());
        // for know what the exception message
        logger.info(exception.getMessage());
        logger.info("==========================================================================");
    }*/


}
