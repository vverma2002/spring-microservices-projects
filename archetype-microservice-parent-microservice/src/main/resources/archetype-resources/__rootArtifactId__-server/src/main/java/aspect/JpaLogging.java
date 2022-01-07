package com.os.user.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Configurable
@Slf4j
@Profile("dev")
public class JpaLogging {

	@Autowired
	private ObjectMapper mapper;

	@Pointcut("execution(* org.springframework.data.repository.CrudRepository+.save(*)) || "
			+ "execution(* org.springframework.data.repository.CrudRepository+.saveAndFlush(*)) || execution(* org.springframework.data.repository.CrudRepository+.save(..)))) ||"
			+ "execution(public !void org.springframework.data.repository.Repository+.*(..)) ")
	public void whenSaveOrUpdate() {
		log.info("whenSaveOrUpdate");

	};

	@Pointcut("execution(* org.springframework.data.repository.CrudRepository+.delete(*))")
	public void whenDelete() {
		log.info("whenDelete");

	};

	@Before("whenSaveOrUpdate() && args(entity)")
	public void beforeSaveOrUpdate(JoinPoint joinPoint, Object entity) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		try {
			log.info("\t=========> beforeSaveOrUpdate method(s): {}, arguments: {} ", signature.getName(),
					mapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			log.error("Error while converting", e);
		}
	}

	@AfterReturning(pointcut = "whenSaveOrUpdate()", returning = "entity")
	public void afterSaveOrUpdate(JoinPoint joinPoint, Object entity) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		try {
			log.info("\t<========= beforeSaveOrUpdate method(s): {}, returning: {} ", signature.getName(),
					mapper.writeValueAsString(entity));
		} catch (JsonProcessingException e) {
			log.error("Error while converting", e);
		}
	}

}