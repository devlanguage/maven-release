package org.third.common.user.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "h3_event")
public class HiEvent extends HiPersistObject {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
