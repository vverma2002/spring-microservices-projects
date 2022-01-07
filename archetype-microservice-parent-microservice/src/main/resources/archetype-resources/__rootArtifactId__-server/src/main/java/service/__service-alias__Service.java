package ${package}.service;

import java.util.List;
import java.util.Optional;

import ${package}.entity.${service-alias}Entity;

public interface ${service-alias}Service {

	public List<${service-alias}Entity> findAll();

	public Optional<${service-alias}Entity> findById(Long ${resource-alias}Id);

	public ${service-alias}Entity saveOrUpdate(${service-alias}Entity ${resource-alias});

	public void deleteById(Long ${resource-alias}Id);

}