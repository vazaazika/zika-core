package br.les.opus.instagram.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "video_pool", schema = "instagram")
public class VideoPool {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_VIDEO_POOL", schema = "instagram")
	private Long id;
	
	@SerializedName("low_resolution")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "low_id")
	private Video lowResolution;
	
	@SerializedName("standard_resolution")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "std_id")
	private Video standardResolution;
	
	@Transient
	public boolean isValid() {
		return  lowResolution != null && lowResolution.isValid()
				&& standardResolution != null && standardResolution.isValid();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Video getLowResolution() {
		return lowResolution;
	}

	public void setLowResolution(Video lowResolution) {
		this.lowResolution = lowResolution;
	}

	public Video getStandardResolution() {
		return standardResolution;
	}

	public void setStandardResolution(Video standardResolution) {
		this.standardResolution = standardResolution;
	}
}
