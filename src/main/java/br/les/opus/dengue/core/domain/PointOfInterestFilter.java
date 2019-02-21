package br.les.opus.dengue.core.domain;

import br.les.opus.auth.core.domain.User;
import br.les.opus.dengue.core.fields.FieldValue;
import com.vividsolutions.jts.geom.Point;

import java.util.Date;
import java.util.List;

public class PointOfInterestFilter{

    private Long id;

    private Point location;

    private String address;

    private String state;

    private String city;

    private String title;

    private String description;

    private Date date;

    private PoiType type;

    private PoiStatusUpdateType poiStatusUpdateType;

    private User userModifiedStatus;

    private User user;

    private List<Picture> pictures;

    private List<FieldValue> fieldValues;

    private Integer upVoteCount;

    private Integer downVoteCount;

    private Vote userVote;

    private Boolean published;

    public PointOfInterestFilter() {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User getUserModifiedStatus() {
        return userModifiedStatus;
    }

    public void setUserModifiedStatus(User userModifiedStatus) {
        this.userModifiedStatus = userModifiedStatus;
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
        PointOfInterestFilter other = (PointOfInterestFilter) obj;
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

    public PoiStatusUpdateType getPoiStatusUpdateType() {
        return poiStatusUpdateType;
    }

    public void setPoiStatusUpdateType(PoiStatusUpdateType poiStatusUpdateType) {
        this.poiStatusUpdateType = poiStatusUpdateType;
    }

    @Override
    public String toString() {
        return "PointOfInterest{" +
                "id=" + id +
                ", location=" + location +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", user=" + user +
                ", pictures=" + pictures +
                ", fieldValues=" + fieldValues +
                ", upVoteCount=" + upVoteCount +
                ", downVoteCount=" + downVoteCount +
                ", userVote=" + userVote +
                ", published=" + published +
                '}';
    }

}