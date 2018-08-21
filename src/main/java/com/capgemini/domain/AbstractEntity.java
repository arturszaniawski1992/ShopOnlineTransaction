package com.capgemini.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class AbstractEntity {
	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedTime;

	/**
	 * This is the method which set creation time.
	 * 
	 * @param Timestamp
	 *            as creation time.
	 * @return creation time of entity.
	 */
	public void setCreatedTime(final Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * This is the method which set update time.
	 * 
	 * @param Timestamp
	 *            as update time.
	 * @return creation time of entity.
	 */
	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
}
