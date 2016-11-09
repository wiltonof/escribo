package com.escribo.common.ebook.model.impl;

import java.sql.Blob;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.escribo.common.ebook.model.ICollection;
import com.escribo.common.ebook.model.IDiscipline;
import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.impl.Permission;
import com.escribo.common.security.model.impl.Role;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "ebooks", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Ebook extends AbstractModel implements IEbook {
	private static final long serialVersionUID = 7902464329704092378L;

	private String 		title;
	private String 		autor;
	private String 		schoolLevel;
	private Integer 	year;
	private String 		edition;
	private ICollection collection;
	private Blob   		ebook;
	private Blob   		thumb;
	private List<IDiscipline> disciplines;
	
	
	@Column(name = "title", length = 250)
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "autor", length = 250)
	@Override
	public String getAutor() {
		return autor;
	}
	
	@Override
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	@Column(name = "school_level", length = 250)
	@Override
	public String getSchoolLevel() {
		return schoolLevel;
	}
	
	@Override
	public void setSchoolLevel(String schoolLevel) {
		this.schoolLevel = schoolLevel;
	}
	
	@Column(name = "year")
	@Override
	public Integer getYear() {
		return year;
	}
	
	@Override
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Column(name = "edition", length = 250)
	@Override
	public String getEdition() {
		return edition;
	}
	
	@Override
	public void setEdition(String edition) {
		this.edition = edition;
	
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH }, targetEntity = Role.class)
	@JoinColumn(name = "collection_id", nullable = false)
	@Override
	public ICollection getCollection() {
		return collection;
	}
	
	@Override
	public void setCollection(ICollection collection) {
		this.collection = collection;
	}

	@Column(name="ebook")
	@Override
	public Blob getEbook() {
		return ebook;
	}

	@Override
	public void setEbook(Blob ebook) {
		this.ebook = ebook;
	}

	@Column(name="thumb")
	@Override
	public Blob getThumb() {
		return thumb;
	}

	@Override
	public void setThumb(Blob thumb) {
		this.thumb = thumb;
	}

	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, targetEntity = Discipline.class)
	@JoinTable(name = "ebook_has_discipline", joinColumns = { @JoinColumn(name = "ebook_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "discipline_id", nullable = false) })
	@Fetch(FetchMode.SUBSELECT)
	@Override
	public List<IDiscipline> getDisciplines() {
		return disciplines;
	}

	@Override
	public void setDisciplines(List<IDiscipline> disciplines) {
		this.disciplines = disciplines;
	}
	
	
}
