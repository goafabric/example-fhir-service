package org.goafabric.fhir.crossfunctional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This Aspect will be invoked around every method that is part of a {@link org.springframework.web.bind.annotation.RestController} annotated class. It will log the method's signature and duration of the call.
 */
@Component
@Aspect
public class DurationLogger {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("execution(public * *(..)) && within(@org.goafabric.fhir.crossfunctional.DurationLog *)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            log.info("{} took {}ms for user: {} , tenant: {}", toString(method), System.currentTimeMillis() - startTime, HttpInterceptor.getUserName(), HttpInterceptor.getTenantId());
        }
    }

    private String toString(final Method method) {
        var parameterTypes = Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(","));
        return String.format("%s.%s(%s)", method.getDeclaringClass().getSimpleName(),
                method.getName(), parameterTypes);
    }

}
