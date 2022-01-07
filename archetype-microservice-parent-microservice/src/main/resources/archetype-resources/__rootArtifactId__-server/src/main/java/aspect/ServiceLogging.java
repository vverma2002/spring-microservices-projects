package com.os.user.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@Profile("dev")
public class ServiceLogging {

	@Autowired
	private ObjectMapper mapper;

//    @Pointcut("within(com.os.user.controller..*) " +
	// "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	@Pointcut("within(com.os.user.service..*)")
	public void pointcut() {
	}

	@Before("pointcut()")
	public void logMethod(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);

		Map<String, Object> parameters = getParameters(joinPoint);

		try {
//            log.info("==> path(s): {}, method(s): {}, arguments: {} ",
//                    mapping.path(), mapping.method(), mapper.writeValueAsString(parameters));
			log.info("\t======> method: {}, arguments: {} ", signature.getName(), mapper.writeValueAsString(parameters));
		} catch (JsonProcessingException e) {
			log.error("Error while converting", e);
		}
	}

	@AfterReturning(pointcut = "pointcut()", returning = "entity")
	public void logMethodAfter(JoinPoint joinPoint, Object entity) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// RequestMapping mapping =
		// signature.getMethod().getAnnotation(RequestMapping.class);

		try {
//        	log.info("<== path(s): {}, method(s): {}, retuning: {}",
//                    mapping.path(), mapping.method(), mapper.writeValueAsString(entity));
			log.info("\t<====== method: {}, retuning: {}", signature.getName(), mapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			log.error("Error while converting", e);
		}
	}

	private Map<String, Object> getParameters(JoinPoint joinPoint) {
		CodeSignature signature = (CodeSignature) joinPoint.getSignature();

		HashMap<String, Object> map = new HashMap<>();

		String[] parameterNames = signature.getParameterNames();

		for (int i = 0; i < parameterNames.length; i++) {
			map.put(parameterNames[i], joinPoint.getArgs()[i]);
		}

		return map;
	}

}