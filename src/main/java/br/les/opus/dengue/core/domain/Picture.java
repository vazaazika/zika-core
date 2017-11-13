package br.les.opus.dengue.core.domain;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;

import br.les.opus.auth.core.domain.AuthorizedUserAware;
import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.IdAware;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "picture")
public class Picture implements IdAware<Long>, AuthorizedUserAware {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_DOCUMENT")
	private Long id;
	
	@NotNull
	@Column(nullable = false, unique = true)
	private String fileName;
	
	/**
	 * Document creation date
	 */
	@NotNull
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "poi_id")
	private PointOfInterest poi;
	
	private String mimeType;
	
	private Integer width;
	
	private Integer height;
	
	@Transient
	@JsonIgnore
	public boolean isOwnedBy(User user) {
		return this.user != null && this.user.equals(user);
	}
	
	@Transient
	public Image getScaledInstance(String basePath, int width, int height) throws IOException {
		String absolutePath = this.getFileName(basePath);
		File file = new File(absolutePath);
		BufferedImage bufferedImage = ImageIO.read(file);
		Image scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return scaledImage;
	}
	
	@Transient
	public InputStream getScaledInstanceStream(String basePath, int width, int height) throws IOException {
		String absolutePath = this.getFileName(basePath);
		File file = new File(absolutePath);
		
		BufferedImage bufferedImage = ImageIO.read(file);
		bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, width, Scalr.OP_ANTIALIAS);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", os);
		InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
		return inputStream;
	}
	
	public Picture() {
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void generateUniqueName(String fileName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UUID.randomUUID());
		buffer.append("-");
		buffer.append(fileName);
		this.fileName = buffer.toString();
	}
	
	@Transient
	@JsonIgnore
	public String getFileName(String basePath) {
		StringBuffer buffer = new StringBuffer(basePath);
		buffer.append(this.fileName);
		return buffer.toString();
	}
	
	public void saveFileInFileSystem(String basePath, InputStream fileInputStream) 
			throws FileNotFoundException, IOException {
		File file = new File(getFileName(basePath));
		FileOutputStream fos = new FileOutputStream(file);
		IOUtils.copy(fileInputStream, fos);
		fos.close();
	}
	
	public void retrieveDimentionsFromFile(String basePath) throws FileNotFoundException, IOException {
		File file = new File(getFileName(basePath));
		BufferedImage image = ImageIO.read(file);
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User author) {
		this.user = author;
	}

	public PointOfInterest getPoi() {
		return poi;
	}

	public void setPoi(PointOfInterest poi) {
		this.poi = poi;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setContentType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
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
		Picture other = (Picture) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
