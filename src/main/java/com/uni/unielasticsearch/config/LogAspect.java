package com.uni.unielasticsearch.config;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(public * com.uni.unielasticsearch.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog() && @annotation(apiOperation)")
    public void doBefore(JoinPoint point, ApiOperation apiOperation){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuilder sb = new StringBuilder();
        //记录参数
        if (point.getArgs() != null) {
            sb.append("args:");
            for (int i = 0; i < point.getArgs().length; i++) {
                sb.append("[" + i + "]" + point.getArgs()[i] + ",");
            }
        }
        log.info("【{}请求】：url={}", request.getMethod(),request.getRequestURL());
        log.info("【{}--{}--请求参数】：{}",point.getSignature().getName(), apiOperation.value(), sb.toString());
    }

    @AfterReturning(returning = "object",pointcut = "webLog() && @annotation(apiOperation)")
    public void doAfterReturning(JoinPoint point, Object object, ApiOperation apiOperation){
        Signature signature = point.getSignature();
        log.info("【{}--{}--返回结果】：{}",signature.getName(),apiOperation.value(),JSONObject.toJSONString(object));
    }
}
