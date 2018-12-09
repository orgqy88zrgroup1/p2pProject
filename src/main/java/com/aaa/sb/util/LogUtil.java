package com.aaa.sb.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * className:LogUtil
 * discription:通知类（切面的实现类）
 * author:wzb
 * createTime:2018-11-23 18:04
 */
@Component
@Aspect
public class LogUtil {

    /**
     * 切入点配置
     */
    @Pointcut(value = "execution(* com.aaa.sb.service.*.*(..))")
    public void pointCutOne(){
    }

    /**
     * 前置通知
     * @param joinPoint
     */
    /*@Before(value = "pointCutOne()")
    public void beforeServLog(JoinPoint joinPoint){
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println("在调用"+name+"类"+joinPoint.getSignature().getName()+"方法之前打印。。。");
    }*/


    /**
     * 通用日志记录方法
     */
    /*@AfterReturning(value = "pointCutOne()")
    public void saveLogAfterReturning(JoinPoint joinPoint){

        String jpName = joinPoint.getSignature().getName();
        System.out.println(joinPoint.getTarget().getClass().getName()+"在执行"+jpName+"方法后，做了"+"日志记录功能");
    }*/

    /**
     * 异常通知
     * @param joinPoint
     * @param ex
     */
    /*@AfterThrowing(value = "pointCutOne()",throwing = "ex")
    public void exceptionAfterThrowing(JoinPoint joinPoint,Exception ex){

        String jpName = joinPoint.getSignature().getName();
        System.out.println(joinPoint.getTarget().getClass().getName()+"该类在执行"+jpName+"方法过程出现了异常，异常名称为"+ex.getClass().getName()+"异常的描述为"+ex.getMessage()+"做了日志记录功能");

    }*/


    /**
     * 最终通知
     */
    /*@After(value = "pointCutOne()")
    public void executeAfter(){

        System.out.println("无论是否有异常都会执行的方法。。。。。");

    }*/


    /**
     * 环绕通知
     * @param
     */
    @Around(value = "pointCutOne()")
    public Object executeAround(ProceedingJoinPoint proceedingJoinPoint){

        Object o = null;
        //调用业务方法
        try {
            System.out.println("执行了"+proceedingJoinPoint.getSignature().getName()+"方法。。");
            //调用业务方法
            o = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return o;
    }/**/



}
