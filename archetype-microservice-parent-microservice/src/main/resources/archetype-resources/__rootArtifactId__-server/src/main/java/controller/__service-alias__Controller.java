package ${package}.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ${package}.dto.${service-alias}DTO;
import ${package}.entity.${service-alias}Entity;
import ${package}.service.${service-alias}Service;
import ${package}.util.MapperUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ${service-alias}Controller {

	private ${service-alias}Service ${resource-alias}Service;

	private MapperUtil mapper;

	@RequestMapping(path = "/${resource-alias}s", method = RequestMethod.GET)
	public ResponseEntity<List<${service-alias}DTO>> findAll() {
		return ResponseEntity.ok(mapper.to${service-alias}DTO(${resource-alias}Service.findAll()));
	}

	@RequestMapping(path = "/${resource-alias}s/{${resource-alias}Id}", method = RequestMethod.GET)
	public ResponseEntity<${service-alias}DTO> findById(@PathVariable Long ${resource-alias}Id) {
		return ResponseEntity.ok(mapper.to${service-alias}DTO(${resource-alias}Service.findById(${resource-alias}Id)
				.orElseThrow(() -> new IllegalArgumentException("Did not find ${service-alias} Id - " + ${resource-alias}Id))));
	}

	@RequestMapping(path = "/${resource-alias}s", method = RequestMethod.POST)
	public ResponseEntity<${service-alias}DTO> save(@Validated @RequestBody ${service-alias}Entity ${resource-alias}) {
		${resource-alias}.set${service-alias}Id(0L); // Forcing for new entity creation
		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.to${service-alias}DTO(${resource-alias}Service.saveOrUpdate(${resource-alias})));
	}

	@RequestMapping(path = "/${resource-alias}s/{${resource-alias}Id}", method = RequestMethod.PUT)
	public ResponseEntity<${service-alias}DTO> update(@PathVariable Long ${resource-alias}Id, @Validated @RequestBody ${service-alias}Entity ${resource-alias}) {
		${resource-alias}.set${service-alias}Id(${resource-alias}Id);
		return ResponseEntity.ok(mapper.to${service-alias}DTO(${resource-alias}Service.saveOrUpdate(${resource-alias})));
	}

	@RequestMapping(path = "/${resource-alias}s/{${resource-alias}Id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmployee(@PathVariable Long ${resource-alias}Id) {
		${resource-alias}Service.deleteById(${resource-alias}Id);
		return ResponseEntity.accepted().body("Deleted ${service-alias} Id - " + ${resource-alias}Id);
	}
}
