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
@Table(name = "picture_pool", schema = "instagram")
public class PicturePool {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_PICTURE_POOL", schema = "instagram")
	private Long id;
	
	@SerializedName("low_resolution")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "low_id")
	private InstagramPicture lowResolution;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "thumbnail_id")
	private InstagramPicture thumbnail;
	
	@SerializedName("standard_resolution")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "std_id")
	private InstagramPicture standardResolution;
	
	@Transient
	public boolean isValid() {
		return  lowResolution != null && lowResolution.isValid()
				&& standardResolution != null && standardResolution.isValid()
				&& thumbnail != null && thumbnail.isValid();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InstagramPicture getLowResolution() {
		return lowResolution;
	}

	public void setLowResolution(InstagramPicture lowResolution) {
		this.lowResolution = lowResolution;
	}

	public InstagramPicture getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(InstagramPicture thumbnail) {
		this.thumbnail = thumbnail;
	}

	public InstagramPicture getStandardResolution() {
		return standardResolution;
	}

	public void setStandardResolution(InstagramPicture standardResolution) {
		this.standardResolution = standardResolution;
	}
	
}
