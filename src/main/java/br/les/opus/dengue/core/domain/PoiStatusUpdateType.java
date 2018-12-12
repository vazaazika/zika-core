package br.les.opus.dengue.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.les.opus.commons.persistence.IdAware;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Entity
@Table(name = "poi_status_update_type")
public class PoiStatusUpdateType implements IdAware<Long> , Serializable {

	public static final Long VERIFICATION = 1l;
	public static final Long REPORTED = 2l;
	public static final Long IN_ANALYSIS = 3l;
	public static final Long TREATED = 4l;

	@Id
	private Long id;

	@NotEmpty
	@Column(length = 500, nullable = false)
	private String description;

	@NotEmpty
	@Column(length = 500, nullable = false)
	private String typeCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public PoiStatusUpdateType() {
		this.id = PoiStatusUpdateType.REPORTED;
		this.description = "Poi Reportado";
		this.typeCode = "REPORTED";
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
