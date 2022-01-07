package ${package}.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ${package}.entity.${service-alias}Entity;
import ${package}.repository.${service-alias}Repository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ${service-alias}ServiceImpl implements ${service-alias}Service {

	private ${service-alias}Repository ${resource-alias}Repository;

	@Override
	public List<${service-alias}Entity> findAll() {
		return ${resource-alias}Repository.findAll();
	}

	@Override
	public Optional<${service-alias}Entity> findById(Long ${resource-alias}Id) {
		return ${resource-alias}Repository.findById(${resource-alias}Id);

	}

	@Override
	public ${service-alias}Entity saveOrUpdate(${service-alias}Entity ${resource-alias}) {
		return ${resource-alias}Repository.save(${resource-alias});
	}

	@Override
	public void deleteById(Long ${resource-alias}Id) {
		${resource-alias}Repository.deleteById(${resource-alias}Id);
	}

}
