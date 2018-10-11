package br.les.opus.dengue.core.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.les.opus.dengue.core.domain.enumeration.PoiStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import com.vividsolutions.jts.geom.Point;

import br.les.opus.auth.core.domain.AuthorizedUserAware;
import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.IdAware;
import br.les.opus.commons.persistence.spatial.GeotaggedObject;
import br.les.opus.dengue.core.fields.FieldValue;
import br.les.opus.dengue.core.json.View;
import br.les.opus.gamification.domain.PerformedTaskAffectedObject;

@Entity
@Table(name = "point_of_interest")
public class PointOfInterest
		implements IdAware<Long>, AuthorizedUserAware, Voteable, GeotaggedObject, PerformedTaskAffectedObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_POI")
	private Long id;

	@NotNull
	@Column(columnDefinition = "Geometry")
	@Type(type = "org.hibernate.spatial.GeometryType")
	private Point location;

	@Column(length = 300)
	private String address;

	@NotEmpty
	@Column(length = 100, nullable = false)
	private String title;

	@NotEmpty
	@Column(length = 500, nullable = false)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "poi_type_id")
	private PoiType type;

	@Enumerated
	@Column(columnDefinition = "smallint", nullable = false)
	private PoiStatus poiStatus;


	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "poi", cascade = CascadeType.ALL)
	private List<Picture> pictures;

	@JsonView(View.PoiDetails.class)
	@OneToMany(mappedBy = "poi", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FieldValue> fieldValues;

	private Integer upVoteCount;

	private Integer downVoteCount;

	@Transient
	private Vote userVote;

	/**
	 * Whether the POI is visible to public or not
	 */
	private Boolean published;

	public PointOfInterest() {
		this.date = new Date();
		this.published = true;
		this.pictures = new ArrayList<>();
		this.fieldValues = new ArrayList<>();
		this.upVoteCount = 0;
		this.downVoteCount = 0;
	}

	public void removeVote(Vote vote) {
		if (vote.isUpVote()) {
			this.decrementUpVote();
		} else {
			this.decrementDownVote();
		}
	}

	public void addVote(Vote vote) {
		if (vote.isUpVote()) {
			this.incrementUpVote();
		} else {
			this.incrementDownVote();
		}
	}

	public void incrementUpVote() {
		this.upVoteCount++;
	}

	public void decrementUpVote() {
		this.upVoteCount--;
	}

	public void incrementDownVote() {
		this.downVoteCount++;
	}

	public void decrementDownVote() {
		this.downVoteCount--;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PoiType getType() {
		return type;
	}

	public void setType(PoiType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> documents) {
		this.pictures = documents;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<FieldValue> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldValue> fieldValues) {
		this.fieldValues = fieldValues;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointOfInterest other = (PointOfInterest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getDownVoteCount() {
		return downVoteCount;
	}

	public void setDownVoteCount(Integer downVoteCount) {
		this.downVoteCount = downVoteCount;
	}

	public Integer getUpVoteCount() {
		return upVoteCount;
	}

	public void setUpVoteCount(Integer upVoteCount) {
		this.upVoteCount = upVoteCount;
	}

	public Vote getUserVote() {
		return userVote;
	}

	public void setUserVote(Vote userVote) {
		this.userVote = userVote;
	}

	public PoiStatus getPoiStatus() {
		return poiStatus;
	}

	public void setPoiStatus(PoiStatus poiStatus) {
		this.poiStatus = poiStatus;
	}

	@Override
	public String toString() {
		return "PointOfInterest [id=" + id + ", type=" + type + ", user=" + user + "]";
	}

	@Override
	public String getObjectType() {
		return PointOfInterest.class.getSimpleName();
	}


}

