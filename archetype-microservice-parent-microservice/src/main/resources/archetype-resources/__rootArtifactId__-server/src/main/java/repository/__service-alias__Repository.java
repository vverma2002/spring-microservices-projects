package ${package}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ${package}.entity.${service-alias}Entity;

@Repository
public interface ${service-alias}Repository extends JpaRepository<${service-alias}Entity, Long> {

	// that's it ... no need to write any code LOL!
	
}
