package com.example.restspring.first;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// advice are action taken by aspect(cross cutting concerns) for join point
// it also tells when to execute - Around, Before, After 
// ProceedingJoinPoint is a special type of joinpoint provided only for around  
@Aspect 
@Component
public class TimeMonitorAspect {

    
    @Around("@annotation(@TimeMonitor)")
    public void logtime(ProceedingJoinPoint joinPoint) { 

        long start = System.currentTimeMillis(); // start time of the code

        try {
            // execute the join point
            joinPoint.proceed();
        }
        catch (Throwable e) {
            System.out.println("Something went wrong during the execution");
        } finally {
            long end = System.currentTimeMillis();

            long totalExecutionTime = end - start;

            System.out.println("Total time of execution of the method is: " + totalExecutionTime + " ms..");
        }
}
