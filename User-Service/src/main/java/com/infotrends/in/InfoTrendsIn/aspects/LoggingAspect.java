package com.infotrends.in.InfoTrendsIn.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    ObjectMapper mapper = new ObjectMapper();

//    @Pointcut("@annotation(Loggable)")
//    public void executable() {}

//    @Around("execution(* com.infotrends.in.InfoTrendsIn.resources..*(..))")

//    @Around("executable()")
    @Around(value="@within(loggable)")
    public Object applyLogging(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringType().toString().concat("." + signature.getName()); //signature.getName();

        log.info("**************************************************************");
        log.info(String.format("Entering the %s method", methodName));

        if(joinPoint.getArgs().length>0 && joinPoint.getArgs().length<=2) {

            log.info("Logging the Request:");
            Arrays.stream(joinPoint.getArgs()).forEach(value -> {
                try {
                    log.info(mapper.writeValueAsString(value));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        Object result = joinPoint.proceed();

        if(result instanceof ResponseEntity && ((ResponseEntity<EntityModel<? super Object>>) result).getBody() instanceof EntityModel) {
            ResponseEntity<EntityModel<? super Object>> responseEntity = (ResponseEntity<EntityModel<? super Object>>) result;
            EntityModel<? super Object> entityModel = responseEntity.getBody();
            log.info(String.format("Returning Entity response with content %s", mapper.writeValueAsString(entityModel.getContent())));
        } else {
            log.info(String.format("Returning response %s", mapper.writeValueAsString(result)));
        }
        log.info(String.format("Exiting the %s method", methodName));
        log.info("**************************************************************");

        return result;
    }


}
