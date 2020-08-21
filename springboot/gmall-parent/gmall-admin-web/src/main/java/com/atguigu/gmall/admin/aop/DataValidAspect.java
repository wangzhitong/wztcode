package com.atguigu.gmall.admin.aop;


import com.atguigu.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


/**
 *
 */
@Slf4j
@Aspect
@Component
public class DataValidAspect {


    @Around("execution(* com.atguigu.gmall.admin..*Controller.*(..))")
    public Object validAround(ProceedingJoinPoint point){
        Object proceed = null;
        try {
            System.out.println();
            log.error("校验切面开始工作。。。");
            Object[] args = point.getArgs();
            for (Object obj:args){
                if (obj instanceof BindingResult){
                    BindingResult bindingResult = (BindingResult) obj;
                    if (bindingResult.getErrorCount() > 0){
                        return new CommonResult().validateFailed(bindingResult);
                    }
                }
            }
            proceed = point.proceed(args);
        }catch (Throwable throwable){
            throw new RuntimeException(throwable);
        }finally {

        }
        return proceed;
    }
}
