package vn.com.viettel.vds.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * AOP for logging out slow queries
 *
 * @author thanhnd59
 */
@Aspect
@Configuration
@Slf4j
public class RepositoryAspect {
    @Value("${application.repository.query-limit-warning-ms:30}")
    private int executionLimitMs;

    @Around("execution(* vn.com.viettel.vds.repository.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String message = joinPoint.getSignature() + " exec in " + executionTime + " ms";
        if (executionTime >= executionLimitMs){
            log.warn(message + " : SLOW QUERY");
        }
        return proceed;
    }
}
