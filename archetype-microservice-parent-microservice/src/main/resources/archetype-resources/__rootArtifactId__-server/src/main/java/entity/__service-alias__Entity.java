package ${package}.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "${resource-alias}_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${service-alias}Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "${resource-alias}_id")
	private Long ${resource-alias}Id;
}
